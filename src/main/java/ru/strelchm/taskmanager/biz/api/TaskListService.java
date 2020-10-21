package ru.strelchm.taskmanager.biz.api;

import ru.strelchm.taskmanager.model.TaskList;

import java.time.LocalDateTime;
import java.util.List;
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
     * @param pageItemCount - Кол-во элементов, выводимых для пагинации (по умолчанию 10)
     * @param sortType      - 1. “CREATE_DATE” 2. “UPDATE_DATE”, 3. “TITLE” 4. Остальные варианты – ошибка входных данных
     *                      5. Нет параметра – без сортировки / либо сортировка по имени – решить при реализации crud-репозиториев)
     * @param sortDir       - Направление сортировки. 1. “ASC” 2. “DESC”, 3.Остальные варианты – ошибка входных данных
     *                      4. Нет параметра и есть параметр sortType – по-дефолту берется “ASC”
     * @param title         – фильтрация по названию списка заданий
     * @param todo          - фильтр по завершенности. При false выводятся списки, в которых все задания завершенные.
     *                      При true – списки, в которых есть хотя бы 1 незавершенное задание
     * @param createDate    – фильтр по дате создания
     * @param updateDate    – фильтр по дате изменения
     * @return
     */
    List<TaskList> getAllTaskLists(int pageItemCount, String sortType, String sortDir, String title,
                                   boolean todo, LocalDateTime createDate, LocalDateTime updateDate);

    /**
     * Создание списка заданий
     *
     * @param taskList
     * @return
     */
    TaskList createTaskList(TaskList taskList);

    /**
     * Обновление списка заданий
     *
     * @param taskList
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
