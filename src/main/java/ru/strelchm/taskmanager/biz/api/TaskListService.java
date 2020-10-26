package ru.strelchm.taskmanager.biz.api;

import org.springframework.data.domain.Pageable;
import ru.strelchm.taskmanager.model.entity.TaskList;
import ru.strelchm.taskmanager.model.response_dto.TaskListResponseDTO;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сервис предоставления бизнес-логики списков заданий
 */
public interface TaskListService {
    /**
     * Получение списка заданий по id
     *
     * @param taskListId - индефикатор ресурса
     * @return
     */
    TaskList getTaskListById(UUID taskListId);

    /**
     * Получение списка всех списков заданий
     *
     * @param pageable   1. Пагинация. Кол-во элементов, выводимых для пагинации (по умолчанию 10)
     *                   2. Сортировка
     * @param title      – фильтрация по названию списка заданий
     * @param createDate – фильтр по дате создания
     * @param updateDate – фильтр по дате изменения
     * @return
     */
    TaskListResponseDTO getAllTaskLists(Pageable pageable, String title, LocalDateTime createDate, LocalDateTime updateDate);

    /**
     * Создание списка заданий
     *
     * @param taskList - входной список заданий
     * @return
     */
    TaskList createTaskList(TaskList taskList);

    /**
     * Обновление списка заданий
     *
     * @param taskList   - входной список заданий
     * @param taskListId - индефикатор ресурса
     * @return
     */
    TaskList updateTaskListById(TaskList taskList, UUID taskListId);

    /**
     * Удаление списка заданий по id
     *
     * @param taskListId - индефикатор ресурса
     */
    void deleteTaskListById(UUID taskListId);
}
