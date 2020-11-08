package ru.strelchm.taskmanager.controller;

import io.swagger.annotations.*;
import org.hibernate.QueryException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.model.dbo.TaskList;
import ru.strelchm.taskmanager.model.dbo.TaskListGroup;
import ru.strelchm.taskmanager.model.dto.TaskListDTO;
import ru.strelchm.taskmanager.model.dto.TaskListGroupDTO;
import ru.strelchm.taskmanager.service.api.TaskListService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

/**
 * REST-контроллер для ресурсов "Список задач"
 */
@RestController
@RequestMapping("/tasklists")
@Api("REST-контроллер для ресурсов \"Список задач\"")
@Validated // валидация @PathVariable и @RequestParam
public class TaskListController {
    public final static int DEFAULT_TASK_LIST_PAGE_SIZE = 10;

    private final TaskListService taskListService; // сервис с бизнес-логикой для заданий
    private final ModelMapper mapper;

    @Autowired
    public TaskListController(TaskListService taskListService, ModelMapper mapper) {
        this.taskListService = taskListService;
        this.mapper = mapper;
    }

    /**
     * Получение всех списков заданий
     *
     * @param pageable   1. Пагинация. Кол-во элементов, выводимых для пагинации (по умолчанию 10)
     *                   2. Сортировка
     * @param title      – фильтрация по названию списка заданий
     * @param createDate – фильтр по дате создания
     * @param updateDate – фильтр по дате изменения
     */
    @ApiOperation("Получение всех списков заданий")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Номер страницы с элементами (0..N). По умолчанию = 0.  Пример: \"2\"\""),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Количество элементов на странице. По умолчанию 10 шт. Пример: \"10\""),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Критерий сортировки в формате: property(,asc|desc). " +
                            "По умолчанию сортировка по id, направление  desc. " +
                            "Поддержка множества критериев сортировки. Пример: \"title,asc\"", example = "title,asc")
    })
    @GetMapping
    public TaskListGroupDTO getAllTaskLists(@ApiIgnore @PageableDefault(size = DEFAULT_TASK_LIST_PAGE_SIZE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                            @ApiParam(name = "title", type = "String", value = "Фильтр по названию списка", example = "Example some title")
                                            @RequestParam(name = "title", required = false) String title,

                                            @ApiParam(name = "createDate", type = "String", value = "Фильтр по дате создания списка в ISO-8601 календарной системе", example = "2020-11-04T16:31:55.492")
                                            @RequestParam(name = "createDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createDate,

                                            @ApiParam(name = "updateDate", type = "String", value = "Фильтр по дате изменения списка в ISO-8601 календарной системе", example = "2020-11-04T16:31:55.492")
                                            @RequestParam(name = "updateDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateDate) {
        TaskListGroup dboResponse = taskListService.getAllTaskLists(pageable, title, createDate, updateDate);
        return mapper.map(dboResponse, TaskListGroupDTO.class);
    }

    @PostMapping
    @ApiOperation("Создание списка заданий")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TaskListDTO createTaskList(
            @ApiParam(name = "title", type = "String", value = "Название списка", example = "Example some title", required = true)
            @Size(min = 0, max = 256, message = "Wrong string size") @NotNull(message = "Name parameter not found") @RequestBody String taskListTitle) {
        TaskList serviceResultDbo;
        TaskList dbo = new TaskList();
        dbo.setTitle(taskListTitle);

        serviceResultDbo = taskListService.createTaskList(dbo);
        return mapper.map(serviceResultDbo, TaskListDTO.class);
    }

    @PutMapping("/{taskListId}")
    @ApiOperation("Изменение списка заданий по идентификатору")
    public TaskListDTO updateTaskList(
            @ApiParam(name = "title", type = "String", value = "Название списка", example = "Example some title", required = true)
            @Size(min = 0, max = 256, message = "Wrong string size") @NotNull(message = "Name parameter not found") @RequestBody String taskListTitle,
            @PathVariable UUID taskListId) {
        TaskList serviceResultDbo;
        TaskList dbo = new TaskList();

        dbo.setTitle(taskListTitle);
        serviceResultDbo = taskListService.updateTaskListById(dbo, taskListId);
        return mapper.map(serviceResultDbo, TaskListDTO.class);
    }

    @DeleteMapping("/{taskListId}")
    @ApiOperation("Удаление списка заданий по идентификатору")
    public void deleteTaskList(@PathVariable UUID taskListId) {
        taskListService.deleteTaskListById(taskListId);
    }

    private static final String BAD_REQUEST_MESSAGE = "Bad request. Check request params";

    /**
     * Перехват 500 ошибки при неверных параметрах запроса
     *
     * @param ex
     * @return 400 код ошибки
     */
    @ExceptionHandler({QueryException.class, DataIntegrityViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect request property, check request")
    public HashMap<String, String> handleBadRequestExceptions(Exception ex) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", BAD_REQUEST_MESSAGE);
        response.put("error", ex.getClass().getSimpleName());
        return response;
    }
}
