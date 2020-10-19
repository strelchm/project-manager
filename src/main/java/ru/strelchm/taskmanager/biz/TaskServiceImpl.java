package ru.strelchm.taskmanager.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.strelchm.taskmanager.biz.api.TaskService;
import ru.strelchm.taskmanager.biz.exception.AlreadyMarkDoneException;
import ru.strelchm.taskmanager.biz.exception.DataNotFoundException;
import ru.strelchm.taskmanager.model.Task;
import ru.strelchm.taskmanager.persistense.repo.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task getTaskById(UUID taskId) {
        Optional<Task> task = taskRepository.findById(taskId);

        if (!task.isPresent()) {
            throw new DataNotFoundException("Задание с id " + taskId + " не найдено в базе данных");
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
        return null;
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
