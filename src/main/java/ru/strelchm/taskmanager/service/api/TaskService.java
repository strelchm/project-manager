package ru.strelchm.taskmanager.service.api;

import ru.strelchm.taskmanager.model.dbo.Task;
import ru.strelchm.taskmanager.model.dbo.TaskGroup;

import java.util.UUID;

/**
 * Сервис предоставления бизнес-логики заданий
 */
public interface TaskService {

    /**
     * Получение задания по id
     *
     * @param taskId
     */
    Task getTaskById(UUID taskId);

    /**
     * Получение списка всех заданий
     */
    TaskGroup getTasksByTaskListIdAndDoneFlag(UUID taskListId, Boolean done);

    /**
     * Создание задания
     *
     * @param task
     */
    Task createTask(Task task);

    /**
     * Обновление задания
     *
     * @param task
     * @param taskId
     */
    Task updateTaskById(Task task, UUID taskId);

    /**
     * Изменение готовности задания на "сделанное"
     *
     * @param taskId
     */
    Task markDoneTaskById(UUID taskId);

    /**
     * Удаление задания по id
     *
     * @param taskId
     */
    void deleteTaskById(UUID taskId);
}
