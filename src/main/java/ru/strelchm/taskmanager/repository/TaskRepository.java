package ru.strelchm.taskmanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.strelchm.taskmanager.model.dbo.TaskDBO;
import ru.strelchm.taskmanager.model.dbo.TaskListDBO;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий заданий
 */
@Repository
public interface TaskRepository extends CrudRepository<TaskDBO, UUID> {
    /**
     * Получение всех заданий по id и выполненности
     *
     * @return - все задания, коллекция Task
     */
    @Query("select t from TaskDBO t where " +
            "t.taskList = :taskList and " +
            "(:done is null or t.done = :done)")
    List<TaskDBO> findAllByTaskListAndDone(
            @Param("taskList") TaskListDBO taskList,
            @Param("done") Boolean done
    );

    /**
     * Получение количества заданий с определенном булевом значении выполненности
     *
     * @param done - выполнено ли
     */
    Long countAllByTaskListAndDone(TaskListDBO taskList, Boolean done);
}
