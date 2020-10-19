package ru.strelchm.taskmanager.biz.api;

import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.model.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    /**
     * Получение задания по id
     * @param taskId
     * @return
     */
    Task getTaskById(UUID taskId);

    /**
     * Получение списка всех заданий
     * @return
     */
    List<Task> getAllTasks();

    /**
     * Создание задания
     * @param task
     * @return
     */
    Task createTask(Task task);

    /**
     * Обновление задания
     * @param task
     * @param taskId
     * @return
     */
    Task updateTaskById(Task task, UUID taskId);

    /**
     * Изменение готовности задания на "сделанное"
     * @param taskId
     * @return
     */
    Task markDoneTaskById(UUID taskId);

    /**
     * Удаление задания по id
     * @param taskId
     */
    void deleteTaskById(UUID taskId);
}
