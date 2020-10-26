package ru.strelchm.taskmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.strelchm.taskmanager.model.entity.TaskList;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Репозиторий списков заданий
 */
public interface TaskListRepository extends PagingAndSortingRepository<TaskList, UUID> {
    /**
     * Получение всех списков заданий
     *
     * @return - все списки заданий, коллекция TaskList
     */
    @Query("select tl from TaskList tl " +
            "where (tl.title = :title or :title is null) and " +
            "(tl.createTime = :createTime or cast(:createTime as java.time.LocalDateTime) is null) and " +
            "(tl.updateTime = :updateTime or cast(:updateTime as java.time.LocalDateTime) is null)")
    Page<TaskList> findByTitleAndAndCreateTimeAAndUpdateTime(
            @Param("title") String title,
            @Param("createTime") LocalDateTime createTime,
            @Param("updateTime") LocalDateTime updateTime,
            Pageable pageable);

    /**
     * Получение количества выполненных списков заданий
     *
     * @param title      - название
     * @param createTime - дата создания
     * @param updateTime - дата изменения
     * @return
     */
    @Query("select count(tl) from TaskList tl join tl.tasks t where " +
            "(tl.title = :title or :title is null) and " +
            "(tl.createTime = :createTime or cast(:createTime as java.time.LocalDateTime) is null) and " +
            "(tl.updateTime = :updateTime or cast(:updateTime as java.time.LocalDateTime) is null) and " +
            "(select count(t) from t where (t.done = false or t.done is null)) = 0")
    Long countAllDoneTaskLists(
            @Param("title") String title,
            @Param("createTime") LocalDateTime createTime,
            @Param("updateTime") LocalDateTime updateTime
    );

//    /**
//     * Получение количества невыполненных списков заданий
//     *
//     * @param title      - название
//     * @param createTime - дата создания
//     * @param updateTime - дата изменения
//     * @return
//     */
//    @Deprecated
//    @Query("select count(tl) from TaskList tl join tl.tasks t where " +
//            "(tl.title = :title or :title is null) and " +
//            "(tl.createTime = :createTime or cast(:createTime as java.time.LocalDateTime) is null) and " +
//            "(tl.updateTime = :updateTime or cast(:updateTime as java.time.LocalDateTime) is null) and " +
//            "(((select count(t) from t) = 0) or (select count(t) from t where (t.done = false or t.done is null) > 0))")
//    Long countAllTodoTaskLists(
//            @Param("title") String title,
//            @Param("createTime") LocalDateTime createTime,
//            @Param("updateTime") LocalDateTime updateTime
//    );
}