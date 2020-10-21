package ru.strelchm.taskmanager.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.strelchm.taskmanager.model.Task;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий заданий
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, UUID> {
    /**
     * Получение всех заданий
     * @return - все задания, коллекция Task
     */
    List<Task> findAll();
}
