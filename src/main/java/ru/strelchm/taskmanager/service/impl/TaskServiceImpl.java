package ru.strelchm.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.strelchm.taskmanager.model.dbo.TaskGroupDBO;
import ru.strelchm.taskmanager.service.api.TaskListService;
import ru.strelchm.taskmanager.service.api.TaskService;
import ru.strelchm.taskmanager.exception.AlreadyMarkDoneException;
import ru.strelchm.taskmanager.exception.DataNotFoundException;
import ru.strelchm.taskmanager.exception.DifferentRequestIdException;
import ru.strelchm.taskmanager.exception.IncorrectNameException;
import ru.strelchm.taskmanager.model.dbo.TaskDBO;
import ru.strelchm.taskmanager.model.dbo.TaskListDBO;
import ru.strelchm.taskmanager.model.dto.TaskGroupDTO;
import ru.strelchm.taskmanager.repository.TaskRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Реализация сервиса придоставления бизнес-логики заданий
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskListService taskListService;

    @Override
    public TaskDBO getTaskById(UUID taskId) {
        Optional<TaskDBO> task = taskRepository.findById(taskId);

        if (!task.isPresent()) {
            throw new DataNotFoundException("Task with id " + taskId + " not found in database");
        }
        return task.get();
    }

    @Override
    public TaskGroupDBO getTasksByTaskListIdAndDoneFlag(UUID taskListId, Boolean done) {
        TaskGroupDBO model = new TaskGroupDBO();
        TaskListDBO taskList = taskListService.getTaskListById(taskListId);

        model.setTasks(taskRepository.findAllByTaskListAndDone(taskList, done));
        model.setDoneTotalTaskCount(taskRepository.countAllByTaskListAndDone(taskList, true));
        model.setTodoTotalTaskCount(taskRepository.countAllByTaskListAndDone(taskList, false));

        return model;
    }

    @Override
    public TaskDBO createTask(TaskDBO task) {
        if (task.getDone() == null) {
            task.setDone(false);
        } else if (task.getDone()) {
            throw new AlreadyMarkDoneException("Created task " + task.getTitle() + " can't be done");
        }
        return taskRepository.save(task);
    }

    @Override
    public TaskDBO updateTaskById(TaskDBO task, UUID taskId) {
        TaskDBO dbTaskDBO;

        if (task.getTitle().isEmpty()) {
            throw new IncorrectNameException("Task request name is empty");
        }

        if (task.getId() != null && !task.getId().equals(taskId)) {
            throw new DifferentRequestIdException("Request task id not equals to path variable");
        }

        dbTaskDBO = this.getTaskById(taskId);
        dbTaskDBO.setTitle(task.getTitle());

        taskRepository.save(dbTaskDBO);

        return dbTaskDBO;
    }

    @Override
    public TaskDBO markDoneTaskById(UUID taskId) {
        TaskDBO dbTaskDBO = this.getTaskById(taskId);

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
