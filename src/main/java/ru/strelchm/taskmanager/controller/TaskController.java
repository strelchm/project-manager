package ru.strelchm.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.biz.api.TaskService;
import ru.strelchm.taskmanager.model.entity.Task;
import ru.strelchm.taskmanager.model.response_dto.TaskResponseDTO;

import javax.validation.Valid;
import java.util.UUID;

/**
 * REST-контроллер для ресурсов "Задача"
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public TaskResponseDTO getAllTasks(@RequestParam(name = "taskListId") UUID taskListId,
                                       @RequestParam(name = "done", required = false) Boolean done) {
        return taskService.getTasksByTaskListIdAndDoneFlag(taskListId, done);
    }

    @PostMapping
    public Task createTask(@RequestBody @Valid Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@RequestBody @Valid Task task, @PathVariable UUID taskId) {
        return taskService.updateTaskById(task, taskId);
    }

    @PutMapping("/markDone/{taskId}")
    public Task markDoneTask(@PathVariable UUID taskId) {
        return taskService.markDoneTaskById(taskId);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTaskById(taskId);
    }

}
