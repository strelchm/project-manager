package ru.strelchm.taskmanager.biz.api;

import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.model.Task;
import ru.strelchm.taskmanager.model.TaskList;

import java.util.List;
import java.util.UUID;

public interface TaskListService {
    /**
     * Получение списка заданий по id
     * @param taskListId
     * @return
     */
    TaskList getTaskListById(UUID taskListId);

    /**
     * Получение списка всех списков заданий
     * @return
     */
    List<TaskList> getAllTaskLists();

    /**
     * Создание списка заданий
     * @param taskList
     * @return
     */
    TaskList createTaskList(TaskList taskList);

    /**
     * Обновление списка заданий
     * @param taskList
     * @param taskListId
     * @return
     */
    TaskList updateTaskListById(TaskList taskList, UUID taskListId);

    /**
     * Удаление списка заданий по id
     * @param taskListId
     */
    void deleteTaskListById(UUID taskListId);
}
