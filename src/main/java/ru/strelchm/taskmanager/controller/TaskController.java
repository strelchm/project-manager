package ru.strelchm.taskmanager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.QueryException;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.exception.IncorrectDataException;
import ru.strelchm.taskmanager.exception.TaskPriorityUnrecognizeException;
import ru.strelchm.taskmanager.model.dbo.Task;
import ru.strelchm.taskmanager.model.dbo.TaskGroup;
import ru.strelchm.taskmanager.model.dbo.TaskList;
import ru.strelchm.taskmanager.model.dbo.TaskPriorityType;
import ru.strelchm.taskmanager.model.dto.TaskDTO;
import ru.strelchm.taskmanager.model.dto.TaskGroupDTO;
import ru.strelchm.taskmanager.model.dto.TaskRequestBodyDTO;
import ru.strelchm.taskmanager.service.api.TaskListService;
import ru.strelchm.taskmanager.service.api.TaskService;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для ресурсов "Задача"
 */
@RestController
@RequestMapping("/tasks")
@Api(description = "REST-контроллер для ресурсов \"Задача\"")
public class TaskController {
    private final TaskService taskService;
    private final TaskListService taskListService;
    private final MapperConfig mapper;

    @Autowired
    public TaskController(TaskService taskService, MapperConfig mapper, TaskListService taskListService) {
        this.mapper = mapper;
        this.taskService = taskService;
        this.taskListService = taskListService;

        mapper.modelMapper().addMappings(new PropertyMap<Task, TaskDTO>() {
            @Override
            protected void configure() {
                skip(destination.getPriority());
            }
        });
    }

    @GetMapping
    @ApiOperation("Получение списка всех заданий")
    public TaskGroupDTO getAllTasks(
            @ApiParam(name = "taskListId", type = "UUID", value = "Идентификатор списка заданий", example = "08827999-f46e-4045-9c2a-59fad8db2cb3")
            @RequestParam(name = "taskListId") UUID taskListId,
            @ApiParam(name = "done", type = "boolean", value = "Фильтр по готовности задания", example = "true")
            @RequestParam(name = "done", required = false) Boolean done) {
        TaskGroup taskDBO = taskService.getTasksByTaskListIdAndDoneFlag(taskListId, done);
        return convertTaskGroupDboToDto(taskDBO);
    }

    @PostMapping
    @ApiOperation("Создание задания")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TaskDTO createTask(@RequestBody @Valid TaskRequestBodyDTO taskRequest) {
        Task serviceResultDbo;
        TaskPriorityType priorityType;
        TaskDTO returnTask;
        Task dbo = new Task();

        dbo.setTitle(taskRequest.getTitle());
        dbo.setDescription(taskRequest.getDescription());

        priorityType = getTaskPriorityTypeByValue(taskRequest);

        if(priorityType == null) {
            priorityType = TaskPriorityType.getDefaultPriorityType();
        }

        dbo.setPriority(priorityType);

        if (taskRequest.getTaskListId() == null) {
            throw new IncorrectDataException("Task list request parameter is not set");
        }

        dbo.setTaskList(taskListService.getTaskListById(taskRequest.getTaskListId()));

        serviceResultDbo = taskService.createTask(dbo);

        returnTask = mapper.modelMapper().map(serviceResultDbo, TaskDTO.class);
        returnTask.setPriority(serviceResultDbo.getPriority().getValue()); //TODO : hardcode
        return returnTask;
    }

    @PutMapping("/{taskId}")
    @ApiOperation("Изменение задания по идентификатору")
    public TaskDTO updateTask(@RequestBody @Valid TaskRequestBodyDTO taskRequest, @PathVariable UUID taskId) {
        Task serviceResultDbo;
        TaskDTO returnTask;
        Task dbo = new Task();

        dbo.setTitle(taskRequest.getTitle());
        dbo.setDescription(taskRequest.getDescription());
        dbo.setPriority(this.getTaskPriorityTypeByValue(taskRequest));

        serviceResultDbo = taskService.updateTaskById(dbo, taskId);

        returnTask = mapper.modelMapper().map(serviceResultDbo, TaskDTO.class);
        returnTask.setPriority(serviceResultDbo.getPriority().getValue()); //TODO : hardcode
        return returnTask;
    }

    @PutMapping("/{taskId}/markDone")
    @ApiOperation("Отметка готовности списка заданий (по идентификатору)")
    public TaskDTO markDoneTask(@PathVariable UUID taskId) {
        Task serviceResultDbo = taskService.markDoneTaskById(taskId);
        TaskDTO returnTask = mapper.modelMapper().map(serviceResultDbo, TaskDTO.class);

        returnTask.setPriority(serviceResultDbo.getPriority().getValue()); //TODO : hardcode
        return returnTask;
    }

    @DeleteMapping("/{taskId}")
    @ApiOperation("Удаление задания по идентификатору")
    public void deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTaskById(taskId);
    }

    private static final String BAD_REQUEST_MESSAGE = "Bad request. Check request params";

    /**
     * Перехват 500 ошибки при неверных параметрах запроса
     *
     * @param ex - 400 код ошибки
     * @return коллекция параметров ошибочного ответа
     */
    @ExceptionHandler({QueryException.class, DataIntegrityViolationException.class, PropertyReferenceException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect request property, check request")
    public HashMap<String, String> handleBadRequestExceptions(Exception ex) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", BAD_REQUEST_MESSAGE);
        response.put("error", ex.getClass().getSimpleName());
        return response;
    }

    /**
     * Получение приоритетности по целочисленному значению
     */
    private TaskPriorityType getTaskPriorityTypeByValue(TaskRequestBodyDTO requestDto) {
        if(requestDto.getPriority() == null) {
            return null;
        }

        TaskPriorityType priorityType = TaskPriorityType.getPriorityTypeByValue(requestDto.getPriority());

        if (priorityType == null) {
            throw new TaskPriorityUnrecognizeException("Can't recognize request priority type that is " + requestDto.getPriority());
        }

        return priorityType;
    }

    /**
     * Конвертирование группового DBO в групповое DTO
     *
     * @param dboResponse
     */
    private TaskGroupDTO convertTaskGroupDboToDto(TaskGroup dboResponse) {
        TaskGroupDTO dtoResponse = new TaskGroupDTO();
        List<TaskDTO> dtoList = new ArrayList<>();
//        Type listType = new TypeToken<List<TaskDTO>>() {
//        }.getType();
//
//        List<TaskDTO> dtoList = mapper.modelMapper().map(dboResponse.getTasks(), listType); // todo сделать через маппер

        for(Task td : dboResponse.getTasks()) {
            TaskDTO task = mapper.modelMapper().map(td, TaskDTO.class);
            task.setPriority(td.getPriority().getValue()); //TODO : hardcode
            dtoList.add(task);
        }

        dtoResponse.setTasks(dtoList);
        dtoResponse.setDoneTotalTaskCount(dboResponse.getDoneTotalTaskCount());
        dtoResponse.setTodoTotalTaskCount(dboResponse.getTodoTotalTaskCount());

        return dtoResponse;
    }
}
