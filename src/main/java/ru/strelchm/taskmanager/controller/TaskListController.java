package ru.strelchm.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.biz.api.TaskListService;
import ru.strelchm.taskmanager.model.TaskList;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasklists")
public class TaskListController {
    @Autowired
    private TaskListService taskListService; // сервис с бизнес-логикой для заданий

    @GetMapping
    public List<TaskList> getAllTaskLists() {
        return taskListService.getAllTaskLists();
    }

    @PostMapping
    public TaskList createTaskList(@RequestBody TaskList taskList) {
        return taskListService.createTaskList(taskList);
    }

    @PutMapping("/{taskListId}")
    public TaskList updateTaskList(@RequestBody TaskList taskList, @PathVariable UUID taskListId) {
        return taskListService.updateTaskListById(taskList, taskListId);
    }

    @DeleteMapping("/{taskListId}")
    public void deleteTaskList(@PathVariable UUID taskListId) {
        taskListService.deleteTaskListById(taskListId);
    }

}
