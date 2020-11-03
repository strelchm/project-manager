package ru.strelchm.taskmanager.service.api;

import org.springframework.data.domain.Pageable;
import ru.strelchm.taskmanager.model.dbo.TaskListDBO;
import ru.strelchm.taskmanager.model.dbo.TaskListGroupDBO;
import ru.strelchm.taskmanager.model.dto.TaskListGroupDTO;

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
     */
    TaskListDBO getTaskListById(UUID taskListId);

    /**
     * Получение списка всех списков заданий
     *
     * @param pageable   1. Пагинация. Кол-во элементов, выводимых для пагинации (по умолчанию 10)
     *                   2. Сортировка
     * @param title      – фильтрация по названию списка заданий
     * @param createDate – фильтр по дате создания
     * @param updateDate – фильтр по дате изменения
     */
    TaskListGroupDBO getAllTaskLists(Pageable pageable, String title, LocalDateTime createDate, LocalDateTime updateDate);

    /**
     * Создание списка заданий
     *
     * @param taskList - входной список заданий
     */
    TaskListDBO createTaskList(TaskListDBO taskList);

    /**
     * Обновление списка заданий
     *
     * @param taskList   - входной список заданий
     * @param taskListId - индефикатор ресурса
     */
    TaskListDBO updateTaskListById(TaskListDBO taskList, UUID taskListId);

    /**
     * Удаление списка заданий по id
     *
     * @param taskListId - индефикатор ресурса
     */
    void deleteTaskListById(UUID taskListId);
}
