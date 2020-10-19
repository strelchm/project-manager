package ru.strelchm.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.biz.api.TaskService;
import ru.strelchm.taskmanager.model.PriorityType;
import ru.strelchm.taskmanager.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public Task createTask(@RequestBody @Valid Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@RequestBody Task task, @PathVariable UUID taskId) {
        return taskService.updateTaskById(task, taskId);
    }

    @PutMapping("/{taskId}")
    public Task markDoneTask(@PathVariable UUID taskId) {
        return taskService.markDoneTaskById(taskId);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTaskById(taskId);
    }

}
