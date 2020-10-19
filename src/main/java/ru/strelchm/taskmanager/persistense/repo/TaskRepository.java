package ru.strelchm.taskmanager.persistense.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.strelchm.taskmanager.model.Task;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends CrudRepository<Task, UUID> {
    //    Task findTaskById(UUID id);
    List<Task> findAll();
}
