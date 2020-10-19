package ru.strelchm.taskmanager.persistense.repo;

import org.springframework.data.repository.CrudRepository;
import ru.strelchm.taskmanager.model.TaskList;

import java.util.List;
import java.util.UUID;

public interface TaskListRepository extends CrudRepository<TaskList, UUID> {
//    TaskList findTaskListById(UUID id);
    List<TaskList> findAll();
}