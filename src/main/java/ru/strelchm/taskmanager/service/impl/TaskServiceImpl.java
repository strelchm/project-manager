package ru.strelchm.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.strelchm.taskmanager.exception.AlreadyMarkDoneException;
import ru.strelchm.taskmanager.exception.DataNotFoundException;
import ru.strelchm.taskmanager.exception.DifferentRequestIdException;
import ru.strelchm.taskmanager.exception.IncorrectDataException;
import ru.strelchm.taskmanager.model.dbo.Task;
import ru.strelchm.taskmanager.model.dbo.TaskGroup;
import ru.strelchm.taskmanager.model.dbo.TaskList;
import ru.strelchm.taskmanager.repository.TaskRepository;
import ru.strelchm.taskmanager.service.api.TaskListService;
import ru.strelchm.taskmanager.service.api.TaskService;

import java.util.Optional;
import java.util.UUID;

/**
 * Реализация сервиса придоставления бизнес-логики заданий
 */
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListService taskListService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskListService taskListService) {
        this.taskRepository = taskRepository;
        this.taskListService = taskListService;
    }

    @Override
    public Task getTaskById(UUID taskId) {
        Optional<Task> task = taskRepository.findById(taskId);

        if (!task.isPresent()) {
            throw new DataNotFoundException("Task with id " + taskId + " not found in database");
        }
        return task.get();
    }

    @Override
    public TaskGroup getTasksByTaskListIdAndDoneFlag(UUID taskListId, Boolean done) {
        TaskGroup model = new TaskGroup();
        TaskList taskList = taskListService.getTaskListById(taskListId);

        model.setTasks(taskRepository.findAllByTaskListAndDone(taskList, done));
        model.setDoneTotalTaskCount(taskRepository.countAllByTaskListAndDone(taskList, true));
        model.setTodoTotalTaskCount(taskRepository.countAllByTaskListAndDone(taskList, false));

        return model;
    }

    @Override
    public Task createTask(Task task) {
        if (task.getDone() == null) {
            task.setDone(false);
        } else if (task.getDone()) {
            throw new AlreadyMarkDoneException("Created task " + task.getTitle() + " can't be done");
        }
        return taskRepository.save(task);
    }

    @Override
    public Task updateTaskById(Task task, UUID taskId) {
        Task dbTaskDBO;

        if (task.getTitle().isEmpty()) {
            throw new IncorrectDataException("Task request name is empty");
        }

        if (task.getId() != null && !task.getId().equals(taskId)) {
            throw new DifferentRequestIdException("Request task id not equals to path variable");
        }

        dbTaskDBO = this.getTaskById(taskId);

        if (task.getTitle() != null) {
            dbTaskDBO.setTitle(task.getTitle());
        }

        if (task.getDescription() != null) {
            dbTaskDBO.setDescription(task.getDescription());
        }

        if (task.getPriority() != null) {
            dbTaskDBO.setPriority(task.getPriority());
        }

        return taskRepository.save(dbTaskDBO);
    }

    @Override
    public Task markDoneTaskById(UUID taskId) {
        Task dbTaskDBO = this.getTaskById(taskId);

        if (dbTaskDBO.getDone()) {
            throw new AlreadyMarkDoneException(dbTaskDBO);
        }

        dbTaskDBO.setDone(true);
        taskRepository.save(dbTaskDBO);

        return dbTaskDBO;
    }

    @Override
    public void deleteTaskById(UUID taskId) {
        taskRepository.delete(this.getTaskById(taskId));
    }
}
