package ru.strelchm.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.strelchm.taskmanager.exception.DataNotFoundException;
import ru.strelchm.taskmanager.exception.DifferentRequestIdException;
import ru.strelchm.taskmanager.exception.IncorrectDataException;
import ru.strelchm.taskmanager.model.dbo.TaskList;
import ru.strelchm.taskmanager.model.dbo.TaskListGroup;
import ru.strelchm.taskmanager.repository.TaskListRepository;
import ru.strelchm.taskmanager.service.api.TaskListService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static ru.strelchm.taskmanager.controller.TaskListController.DEFAULT_TASK_LIST_PAGE_SIZE;

/**
 * Реализация сервиса предоставления бизнес-логики списков заданий
 */
@Service
public class TaskListServiceImpl implements TaskListService {
    @Autowired
    private TaskListRepository taskListRepository;

    @Override
    public TaskList getTaskListById(UUID taskListId) {
        Optional<TaskList> taskList = taskListRepository.findById(taskListId);

        if (!taskList.isPresent()) {
            throw new DataNotFoundException("Task list with id " + taskListId + " not found in database");
        }
        return taskList.get();
    }

    //    @PageableAsQueryParam // для маппинга Pageable в Swagger
    @Override
    public TaskListGroup getAllTaskLists(Pageable pageable, String title, LocalDateTime createDate, LocalDateTime updateDate) {
        System.out.println("yes");

        TaskListGroup taskListGroup = new TaskListGroup();

        if (pageable.getPageSize() < 0 || pageable.getPageSize() > 100) {
            pageable = PageRequest.of(pageable.getPageNumber(), DEFAULT_TASK_LIST_PAGE_SIZE, pageable.getSort());
        }

        taskListGroup.setTaskLists(taskListRepository.findByTitleAndAndCreateTimeAAndUpdateTime(title, createDate, updateDate, pageable));
        taskListGroup.setDoneTaskListCount(taskListRepository.countAllDoneTaskLists(title, createDate, updateDate));
        taskListGroup.setTodoTaskListCount(taskListGroup.getTaskLists().getTotalElements() - taskListGroup.getDoneTaskListCount());

        System.out.println("no");

        return taskListGroup;
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        return taskListRepository.save(taskList);
    }

    @Override
    public TaskList updateTaskListById(TaskList taskList, UUID taskListId) {
        TaskList dbTaskList;

        if (taskList.getTitle().isEmpty()) {
            throw new IncorrectDataException("Task list request name is empty");
        }

        if (taskList.getId() != null && !taskList.getId().equals(taskListId)) {
            throw new DifferentRequestIdException("Request task id not equals to path variable");
        }

        dbTaskList = this.getTaskListById(taskListId);
        dbTaskList.setTitle(taskList.getTitle());

        return taskListRepository.save(dbTaskList);
    }

    @Override
    public void deleteTaskListById(UUID taskListId) {
        taskListRepository.delete(this.getTaskListById(taskListId));
    }
}
