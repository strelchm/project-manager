package ru.strelchm.taskmanager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.model.dbo.TaskListDBO;
import ru.strelchm.taskmanager.model.dbo.TaskListGroupDBO;
import ru.strelchm.taskmanager.model.dto.TaskListDTO;
import ru.strelchm.taskmanager.model.dto.TaskListGroupDTO;
import ru.strelchm.taskmanager.service.api.TaskListService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * REST-контроллер для ресурсов "Список задач"
 */
@RestController
@RequestMapping("/tasklists")
@Api(description = "REST-контроллер для ресурсов \"Список задач\"")
@Validated // валидация @PathVariable и @RequestParam
public class TaskListController {
    public final static int DEFAULT_TASK_LIST_PAGE_SIZE = 10;
     * Получение всех списков заданий

    @Autowired
    private TaskListService taskListService; // сервис с бизнес-логикой для заданий
    @Autowired
    private MapperConfig mapper;

    /**
     * Получение всех списков заданий
     *
     * @param pageable   1. Пагинация. Кол-во элементов, выводимых для пагинации (по умолчанию 10)
     *                   2. Сортировка
     * @param title      – фильтрация по названию списка заданий
     * @param createDate – фильтр по дате создания
     * @param updateDate – фильтр по дате изменения
     */
//    @ExceptionHandler(PropertyReferenceException.class) // вместо 500 ошибки, если ошибочные данные в Pageable, напр. в значении сортировки или названии поля сортировки
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect request property, check request")
    @ApiOperation("Получение всех списков заданий")
    @GetMapping
    public TaskListGroupDTO getAllTaskLists(@PageableDefault(size = DEFAULT_TASK_LIST_PAGE_SIZE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                            @RequestParam(name = "title", required = false) String title,
                                            @RequestParam(name = "createDate", required = false) LocalDateTime createDate,
                                            @RequestParam(name = "updateDate", required = false) LocalDateTime updateDate) {
        TaskListGroupDBO dboResponse = taskListService.getAllTaskLists(pageable, title, createDate, updateDate);
        return convertTaskLIstGroupDboToDto(dboResponse);
    }

    /**
     * Конвертирование группового DBO в групповое DTO
     * @param dboResponse
     * @return
     */
    private TaskListGroupDTO convertTaskLIstGroupDboToDto(TaskListGroupDBO dboResponse) {
        TaskListGroupDTO dtoResponse = new TaskListGroupDTO();
        Page<TaskListDTO> page = dboResponse.getTaskLists().map(dbo -> {
            final TaskListDTO dto = new TaskListDTO();
            dto.setTitle(dbo.getTitle());
            dto.setTasks(dbo.getTasks());
            return dto;
        });

        dtoResponse.setTaskLists(page);
        dtoResponse.setDoneTaskListCount(dboResponse.getDoneTaskListCount());
        dtoResponse.setTodoTaskListCount(dboResponse.getTodoTaskListCount());

        return dtoResponse;
    }

    @ApiOperation("Получение всех списков заданий")
    @PostMapping
    public TaskListDTO createTaskList(
            @ApiParam(name = "taskListDto", type = "String", value = "...",example = "Vava", required = true) @RequestBody @Valid TaskListDTO taskListDto) {
        TaskListDBO serviceResultDbo;
        TaskListDBO dbo = new TaskListDBO();
        dbo.setTitle(taskListDto.getTitle());

        serviceResultDbo = taskListService.createTaskList(dbo);
        return mapper.modelMapper().map(serviceResultDbo, TaskListDTO.class);
    }

    @PutMapping("/{taskListId}")
    public TaskListDTO updateTaskList(@RequestBody @Valid TaskListDTO taskListDto, @PathVariable UUID taskListId) {
        TaskListDBO serviceResultDbo;
        TaskListDBO dbo = new TaskListDBO();

        dbo.setTitle(dbo.getTitle());
        serviceResultDbo = taskListService.updateTaskListById(dbo, taskListId);
        return mapper.modelMapper().map(serviceResultDbo, TaskListDTO.class);
    }

    @DeleteMapping("/{taskListId}")
    public void deleteTaskList(@PathVariable UUID taskListId) {
        taskListService.deleteTaskListById(taskListId);
    }

}
