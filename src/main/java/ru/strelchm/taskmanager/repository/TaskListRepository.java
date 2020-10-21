package ru.strelchm.taskmanager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.strelchm.taskmanager.model.TaskList;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий списков заданий
 */
public interface TaskListRepository extends CrudRepository<TaskList, UUID> { // todo - сделать PageRepository
    /**
     * Получение всех списков заданий
     * @return - все списки заданий, коллекция TaskList
     */
    List<TaskList> findAll();
}