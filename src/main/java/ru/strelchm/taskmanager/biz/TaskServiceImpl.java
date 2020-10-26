package ru.strelchm.taskmanager.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.strelchm.taskmanager.biz.api.TaskListService;
import ru.strelchm.taskmanager.biz.api.TaskService;
import ru.strelchm.taskmanager.biz.exception.AlreadyMarkDoneException;
import ru.strelchm.taskmanager.biz.exception.DataNotFoundException;
import ru.strelchm.taskmanager.biz.exception.DifferentRequestIdException;
import ru.strelchm.taskmanager.biz.exception.IncorrectNameException;
import ru.strelchm.taskmanager.model.entity.Task;
import ru.strelchm.taskmanager.model.entity.TaskList;
import ru.strelchm.taskmanager.model.response_dto.TaskResponseDTO;
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
    public Task getTaskById(UUID taskId) {
        Optional<Task> task = taskRepository.findById(taskId);

        if (!task.isPresent()) {
            throw new DataNotFoundException("Task with id " + taskId + " not found in database");
        }
        return task.get();
    }

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect request property, check request")
    @Override
    public TaskResponseDTO getTasksByTaskListIdAndDoneFlag(UUID taskListId, Boolean done) {
        TaskResponseDTO model = new TaskResponseDTO();
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
        Task dbTask;

        if (task.getTitle().isEmpty()) {
            throw new IncorrectNameException("Task request name is empty");
        }

        if (task.getId() != null && !task.getId().equals(taskId)) {
            throw new DifferentRequestIdException("Request task id not equals to path variable");
        }

        dbTask = this.getTaskById(taskId);
        dbTask.setTitle(task.getTitle());

        taskRepository.save(dbTask);

        return dbTask;
    }

    @Override
    public Task markDoneTaskById(UUID taskId) {
        Task dbTask = this.getTaskById(taskId);

        if (dbTask.getDone()) {
            throw new AlreadyMarkDoneException(dbTask);
        }

        dbTask.setDone(true);
        taskRepository.save(dbTask);

        return dbTask;
    }

    @Override
    public void deleteTaskById(UUID taskId) {
        taskRepository.delete(this.getTaskById(taskId));
    }
}
