package ru.strelchm.taskmanager.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.strelchm.taskmanager.biz.api.TaskListService;
import ru.strelchm.taskmanager.biz.exception.DataNotFoundException;
import ru.strelchm.taskmanager.biz.exception.IncorrectNameException;
import ru.strelchm.taskmanager.model.TaskList;
import ru.strelchm.taskmanager.persistense.repo.TaskListRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service //todo - разобраться с анноацией
public class TaskListServiceImpl implements TaskListService {
    @Autowired
    private TaskListRepository taskListRepository;

    @Override
    public TaskList getTaskListById(UUID taskListId) {
        Optional<TaskList> taskList = taskListRepository.findById(taskListId);

        if (!taskList.isPresent()) {
            throw new DataNotFoundException("Список заданий с id " + taskListId + " не найден в базе данных");
        }
        return taskList.get();
    }

    @Override
    public List<TaskList> getAllTaskLists() {
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
