package ru.strelchm.taskmanager.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.strelchm.taskmanager.biz.api.TaskService;
import ru.strelchm.taskmanager.biz.exception.AlreadyMarkDoneException;
import ru.strelchm.taskmanager.biz.exception.DataNotFoundException;
import ru.strelchm.taskmanager.biz.exception.IncorrectNameException;
import ru.strelchm.taskmanager.model.Task;
import ru.strelchm.taskmanager.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Реализация сервиса придоставления бизнес-логики заданий
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task getTaskById(UUID taskId) {
        Optional<Task> task = taskRepository.findById(taskId);

        if (!task.isPresent()) {
            throw new DataNotFoundException("Task with id " + taskId + " not found in database");
        }
        return task.get();
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTaskById(Task task, UUID taskId) {
        Task dbTask;

        if (task.getTitle().isEmpty()) {
            throw new IncorrectNameException("Task request name is empty");
        }

        dbTask = this.getTaskById(taskId);
        dbTask.setTitle(task.getTitle());

        return dbTask;
    }

    @Override
    public Task markDoneTaskById(UUID taskId) {
        Task dbTask = this.getTaskById(taskId);

        if(dbTask.isDone()) {
            throw new AlreadyMarkDoneException(dbTask);
        }

        dbTask.setDone(true);
        return dbTask;
    }

    @Override
    public void deleteTaskById(UUID taskId) {
        taskRepository.delete(this.getTaskById(taskId));
    }
}
