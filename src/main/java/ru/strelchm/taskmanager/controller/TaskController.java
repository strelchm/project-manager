package ru.strelchm.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.model.dbo.TaskDBO;
import ru.strelchm.taskmanager.model.dbo.TaskGroupDBO;
import ru.strelchm.taskmanager.model.dto.TaskDTO;
import ru.strelchm.taskmanager.service.api.TaskService;
import ru.strelchm.taskmanager.model.dto.TaskGroupDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import org.modelmapper.TypeToken;
import java.lang.reflect.Type;

/**
 * REST-контроллер для ресурсов "Задача"
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private MapperConfig mapper;

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect request property, check request")
    @GetMapping
    public TaskGroupDTO getAllTasks(@RequestParam(name = "taskListId") UUID taskListId,
                                    @RequestParam(name = "done", required = false) Boolean done) {
        TaskGroupDBO taskDBO = taskService.getTasksByTaskListIdAndDoneFlag(taskListId, done);
        return convertTaskGroupDboToDto(taskDBO);
    }

    /**
     * Конвертирование группового DBO в групповое DTO
     * @param dboResponse
     * @return
     */
    private TaskGroupDTO convertTaskGroupDboToDto(TaskGroupDBO dboResponse) {
        TaskGroupDTO dtoResponse = new TaskGroupDTO();

        Type listType = new TypeToken<List<TaskGroupDTO>>(){}.getType();
        List<TaskDTO> dtoList = mapper.modelMapper().map(dboResponse.getTasks(),listType);

        dtoResponse.setTasks(dtoList);
        dtoResponse.setDoneTotalTaskCount(dboResponse.getDoneTotalTaskCount());
        dtoResponse.setTodoTotalTaskCount(dboResponse.getTodoTotalTaskCount());

        return dtoResponse;
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid TaskDTO taskDto) {
        TaskDBO serviceResultDbo;
        TaskDBO dbo = new TaskDBO();
        dbo.setTitle(taskDto.getTitle());
        dbo.setDescription(taskDto.getDescription());
        dbo.setPriority(taskDto.getPriority());
        dbo.setDone(taskDto.getDone());
        dbo.setTaskList(taskDto.getTaskList());

        serviceResultDbo = taskService.createTask(dbo);
        return mapper.modelMapper().map(serviceResultDbo, TaskDTO.class);
    }

    @PutMapping("/{taskId}")
    public TaskDTO updateTask(@RequestBody @Valid TaskDTO taskDto, @PathVariable UUID taskId) {
        TaskDBO dbo = new TaskDBO();
        dbo.setTitle(taskDto.getTitle());
        dbo.setDescription(taskDto.getDescription());
        dbo.setPriority(taskDto.getPriority());
        dbo.setDone(taskDto.getDone());
        dbo.setTaskList(taskDto.getTaskList());

        TaskDBO serviceResultDbo = taskService.updateTaskById(dbo, taskId);
        return mapper.modelMapper().map(serviceResultDbo, TaskDTO.class);
    }

    @PutMapping("/markDone/{taskId}")
    public TaskDTO markDoneTask(@PathVariable UUID taskId) {
        TaskDBO serviceResultDbo = taskService.markDoneTaskById(taskId);
        return mapper.modelMapper().map(serviceResultDbo, TaskDTO.class);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTaskById(taskId);
    }

}
