package ru.strelchm.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.biz.api.TaskListService;
import ru.strelchm.taskmanager.model.TaskList;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для ресурсов "Список задач"
 */
@RestController
@RequestMapping("/tasklists")
@Validated // валидация @PathVariable и @RequestParam
public class TaskListController {
    @Autowired
    private TaskListService taskListService; // сервис с бизнес-логикой для заданий

    /**
     * Получение всех списков заданий
     *
     * @param pageItemCount - Кол-во элементов, выводимых для пагинации (по умолчанию 10)
     * @param sortType      - 1. “CREATE_DATE” 2. “UPDATE_DATE”, 3. “TITLE” 4. Остальные варианты – ошибка входных данных
     *                      5. Нет параметра – без сортировки / либо сортировка по имени – решить при реализации crud-репозиториев)
     * @param sortDir       - Направление сортировки. 1. “ASC” 2. “DESC”, 3.Остальные варианты – ошибка входных данных
     *                      4. Нет параметра и есть параметр sortType – по-дефолту берется “ASC”
     * @param title         – фильтрация по названию списка заданий
     * @param todo          - фильтр по завершенности. При false выводятся списки, в которых все задания завершенные.
     *                      При true – списки, в которых есть хотя бы 1 незавершенное задание
     * @param createDate    – фильтр по дате создания
     * @param updateDate    – фильтр по дате изменения
     * @return
     */
    @GetMapping
    public List<TaskList> getAllTaskLists(@PathParam("pageItemCount") int pageItemCount,
                                          @PathParam("sortType") @Size(min = 0, max = 10) String sortType,
                                          @PathParam("sortDir") @Size(min = 0, max = 10) String sortDir,
                                          @PathParam("title") String title,
                                          @PathParam("todo") boolean todo,
                                          @PathParam("createDate") LocalDateTime createDate,
                                          @PathParam("updateDate") LocalDateTime updateDate) {
        return taskListService.getAllTaskLists(pageItemCount, sortType, sortDir, title, todo, createDate, updateDate);
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
