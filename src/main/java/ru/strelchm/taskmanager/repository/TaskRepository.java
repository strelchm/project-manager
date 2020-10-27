package ru.strelchm.taskmanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.strelchm.taskmanager.model.entity.Task;
import ru.strelchm.taskmanager.model.entity.TaskList;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий заданий
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, UUID> {
    /**
     * Получение всех заданий по id и выполненности
     *
     * @return - все задания, коллекция Task
     */
    @Query("select t from Task t where " +
            "t.taskList = :taskList and " +
            "(:done is null or t.done = :done)")
    List<Task> findAllByTaskListAndDone(
            @Param("taskList") TaskList taskList,
            @Param("done") Boolean done
    );

    /**
     * Получение количества заданий с определенном булевом значении выполненности
     *
     * @param done - выполнено ли
     * @return
     */
    Long countAllByTaskListAndDone(TaskList taskList, Boolean done);
}
