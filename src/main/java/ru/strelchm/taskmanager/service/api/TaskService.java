package ru.strelchm.taskmanager.service.api;

import ru.strelchm.taskmanager.model.dbo.TaskDBO;
import ru.strelchm.taskmanager.model.dbo.TaskGroupDBO;
import ru.strelchm.taskmanager.model.dto.TaskGroupDTO;

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
    TaskDBO getTaskById(UUID taskId);

    /**
     * Получение списка всех заданий
     */
    TaskGroupDBO getTasksByTaskListIdAndDoneFlag(UUID taskListId, Boolean done);

    /**
     * Создание задания
     *
     * @param task
     */
    TaskDBO createTask(TaskDBO task);

    /**
     * Обновление задания
     *
     * @param task
     * @param taskId
     */
    TaskDBO updateTaskById(TaskDBO task, UUID taskId);

    /**
     * Изменение готовности задания на "сделанное"
     *
     * @param taskId
     */
    TaskDBO markDoneTaskById(UUID taskId);

    /**
     * Удаление задания по id
     *
     * @param taskId
     */
    void deleteTaskById(UUID taskId);
}
