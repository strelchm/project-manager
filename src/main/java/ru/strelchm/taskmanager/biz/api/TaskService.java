package ru.strelchm.taskmanager.biz.api;

import ru.strelchm.taskmanager.model.entity.Task;
import ru.strelchm.taskmanager.model.response_dto.TaskResponseDTO;

import java.util.UUID;

/**
 * Сервис предоставления бизнес-логики заданий
 */
public interface TaskService {

    /**
     * Получение задания по id
     *
     * @param taskId
     * @return
     */
    Task getTaskById(UUID taskId);

    /**
     * Получение списка всех заданий
     *
     * @return
     */
    TaskResponseDTO getTasksByTaskListIdAndDoneFlag(UUID taskListId, Boolean done);

    /**
     * Создание задания
     *
     * @param task
     * @return
     */
    Task createTask(Task task);

    /**
     * Обновление задания
     *
     * @param task
     * @param taskId
     * @return
     */
    Task updateTaskById(Task task, UUID taskId);

    /**
     * Изменение готовности задания на "сделанное"
     *
     * @param taskId
     * @return
     */
    Task markDoneTaskById(UUID taskId);

    /**
     * Удаление задания по id
     *
     * @param taskId
     */
    void deleteTaskById(UUID taskId);
}
