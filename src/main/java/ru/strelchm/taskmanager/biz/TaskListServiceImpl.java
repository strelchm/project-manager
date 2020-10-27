package ru.strelchm.taskmanager.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.strelchm.taskmanager.biz.api.TaskListService;
import ru.strelchm.taskmanager.biz.exception.DataNotFoundException;
import ru.strelchm.taskmanager.biz.exception.DifferentRequestIdException;
import ru.strelchm.taskmanager.biz.exception.IncorrectNameException;
import ru.strelchm.taskmanager.model.entity.TaskList;
import ru.strelchm.taskmanager.model.response_dto.TaskListResponseDTO;
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
    public TaskList getTaskListById(UUID taskListId) {
        Optional<TaskList> taskList = taskListRepository.findById(taskListId);

        if (!taskList.isPresent()) {
            throw new DataNotFoundException("Task list with id " + taskListId + " not found in database");
        }
        return taskList.get();
    }

    @ExceptionHandler(PropertyReferenceException.class) // вместо 500 ошибки, если ошибочные данные в Pageable, напр. в значении сортировки или названии поля сортировки
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect request property, check request")
//    @PageableAsQueryParam // для маппинга Pageable в Swagger
    @Override
    public TaskListResponseDTO getAllTaskLists(Pageable pageable, String title, LocalDateTime createDate, LocalDateTime updateDate) {
        TaskListResponseDTO taskListResponseDTO = new TaskListResponseDTO();

        if (pageable.getPageSize() < 0 || pageable.getPageSize() > 100) {
            pageable = PageRequest.of(pageable.getPageNumber(), DEFAULT_TASK_LIST_PAGE_SIZE, pageable.getSort());
        }

        taskListResponseDTO.setTaskLists(taskListRepository.findByTitleAndAndCreateTimeAAndUpdateTime(title, createDate, updateDate, pageable));
        taskListResponseDTO.setDoneTaskListCount(taskListRepository.countAllDoneTaskLists(title, createDate, updateDate));
        taskListResponseDTO.setTodoTaskListCount(taskListResponseDTO.getTaskLists().getTotalElements() - taskListResponseDTO.getDoneTaskListCount());

        return taskListResponseDTO;
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
