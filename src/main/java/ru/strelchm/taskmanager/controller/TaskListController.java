package ru.strelchm.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.biz.api.TaskListService;
import ru.strelchm.taskmanager.model.entity.TaskList;
import ru.strelchm.taskmanager.model.response_dto.TaskListResponseDTO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * REST-контроллер для ресурсов "Список задач"
 */
@RestController
@RequestMapping("/tasklists")
@Validated // валидация @PathVariable и @RequestParam
public class TaskListController {
    public final static int DEFAULT_TASK_LIST_PAGE_SIZE = 10;

    @Autowired
    private TaskListService taskListService; // сервис с бизнес-логикой для заданий

    /**
     * Получение всех списков заданий
     *
     * @param pageable   1. Пагинация. Кол-во элементов, выводимых для пагинации (по умолчанию 10)
     *                   2. Сортировка
     * @param title      – фильтрация по названию списка заданий
     * @param createDate – фильтр по дате создания
     * @param updateDate – фильтр по дате изменения
     * @return
     */
    @GetMapping
    public TaskListResponseDTO getAllTaskLists(@PageableDefault(size = DEFAULT_TASK_LIST_PAGE_SIZE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                               @RequestParam(name = "title", required = false) String title,
                                               @RequestParam(name = "createDate", required = false) LocalDateTime createDate,
                                               @RequestParam(name = "updateDate", required = false) LocalDateTime updateDate) {
        return taskListService.getAllTaskLists(pageable, title, createDate, updateDate);
    }

    @PostMapping
    public TaskList createTaskList(@RequestBody @Valid TaskList taskList) {
        return taskListService.createTaskList(taskList);
    }

    @PutMapping("/{taskListId}")
    public TaskList updateTaskList(@RequestBody @Valid TaskList taskList, @PathVariable UUID taskListId) {
        return taskListService.updateTaskListById(taskList, taskListId);
    }

    @DeleteMapping("/{taskListId}")
    public void deleteTaskList(@PathVariable UUID taskListId) {
        taskListService.deleteTaskListById(taskListId);
    }

}
