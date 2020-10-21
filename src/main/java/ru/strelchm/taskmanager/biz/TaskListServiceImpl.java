package ru.strelchm.taskmanager.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.strelchm.taskmanager.biz.api.TaskListService;
import ru.strelchm.taskmanager.biz.exception.DataNotFoundException;
import ru.strelchm.taskmanager.biz.exception.IncorrectNameException;
import ru.strelchm.taskmanager.model.TaskList;
import ru.strelchm.taskmanager.repository.TaskListRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Реализация сервиса предоставления бизнес-логики списков заданий
 */
@Service //todo - разобраться с аннотацией
public class TaskListServiceImpl implements TaskListService {
    @Autowired
    private TaskListRepository taskListRepository;

    @Override
    public TaskList getTaskListById(UUID taskListId) {
        Optional<TaskList> taskList = taskListRepository.findById(taskListId);

        if (!taskList.isPresent()) {
            throw new DataNotFoundException("Tasklist with id " + taskListId + " not found in database");
        }
        return taskList.get();
    }

    @Override
    public List<TaskList> getAllTaskLists(int pageItemCount, String sortType, String sortDir, String title,
                                          boolean todo, LocalDateTime createDate, LocalDateTime updateDate) {
        // todo: реализовать пагинацию, фильтрацию, сортировку
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        return taskListRepository.save(taskList);
    }

    @Override
    public TaskList updateTaskListById(TaskList taskList, UUID taskListId) {
        TaskList dbTaskList;

        if (taskList.getTitle().isEmpty()) {
            throw new IncorrectNameException("Task list request name is empty");
        }

        dbTaskList = this.getTaskListById(taskListId);
        dbTaskList.setTitle(taskList.getTitle());

        return dbTaskList;
    }

    @Override
    public void deleteTaskListById(UUID taskListId) {
        taskListRepository.delete(this.getTaskListById(taskListId));
    }
}
