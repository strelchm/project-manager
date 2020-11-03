package ru.strelchm.taskmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.strelchm.taskmanager.model.dbo.TaskListGroupDBO;
import ru.strelchm.taskmanager.service.api.TaskListService;
import ru.strelchm.taskmanager.exception.DataNotFoundException;
import ru.strelchm.taskmanager.exception.DifferentRequestIdException;
import ru.strelchm.taskmanager.exception.IncorrectNameException;
import ru.strelchm.taskmanager.model.dbo.TaskListDBO;
import ru.strelchm.taskmanager.model.dto.TaskListGroupDTO;
import ru.strelchm.taskmanager.repository.TaskListRepository;

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
    public TaskListDBO getTaskListById(UUID taskListId) {
        Optional<TaskListDBO> taskList = taskListRepository.findById(taskListId);

        if (!taskList.isPresent()) {
            throw new DataNotFoundException("Task list with id " + taskListId + " not found in database");
        }
        return taskList.get();
    }

//    @PageableAsQueryParam // для маппинга Pageable в Swagger
    @Override
    public TaskListGroupDBO getAllTaskLists(Pageable pageable, String title, LocalDateTime createDate, LocalDateTime updateDate) {
        System.out.println("yes");

        TaskListGroupDBO taskListGroup = new TaskListGroupDBO();

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
    public TaskListDBO createTaskList(TaskListDBO taskList) {
        return taskListRepository.save(taskList);
    }

    @Override
    public TaskListDBO updateTaskListById(TaskListDBO taskList, UUID taskListId) {
        TaskListDBO dbTaskList;

        if (taskList.getTitle().isEmpty()) {
            throw new IncorrectNameException("Task list request name is empty");
        }

        if (taskList.getId() != null && !taskList.getId().equals(taskListId)) {
            throw new DifferentRequestIdException("Request task id not equals to path variable");
        }

        dbTaskList = this.getTaskListById(taskListId);
        dbTaskList.setTitle(taskList.getTitle());

        taskListRepository.save(dbTaskList);

        return dbTaskList;
    }

    @Override
    public void deleteTaskListById(UUID taskListId) {
        taskListRepository.delete(this.getTaskListById(taskListId));
    }
}
