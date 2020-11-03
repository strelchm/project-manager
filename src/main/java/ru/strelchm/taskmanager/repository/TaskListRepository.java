package ru.strelchm.taskmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.strelchm.taskmanager.model.dbo.TaskListDBO;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Репозиторий списков заданий
 */
public interface TaskListRepository extends PagingAndSortingRepository<TaskListDBO, UUID> {
    /**
     * Получение всех списков заданий
     *
     * @return - все списки заданий, коллекция TaskList
     */
    @Query("select tl from TaskListDBO tl " +
            "where (tl.title = :title or :title is null) and " +
            "(cast(:createTime as java.time.LocalDateTime) is null or tl.createTime = :createTime) and " +
            "(cast(:updateTime as java.time.LocalDateTime) is null or tl.updateTime = :updateTime)")
    Page<TaskListDBO> findByTitleAndAndCreateTimeAAndUpdateTime(
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
     */
    @Query("select count(tl) from TaskListDBO tl join tl.tasks t where " +
            "(tl.title = :title or :title is null) and " +
            "(cast(:createTime as java.time.LocalDateTime) is null or tl.createTime = :createTime) and " +
            "(cast(:updateTime as java.time.LocalDateTime) is null or tl.updateTime = :updateTime) and " +
            "(select count(t) from t where (t.done is null or t.done = false)) = 0")
    Long countAllDoneTaskLists(
            @Param("title") String title,
            @Param("createTime") LocalDateTime createTime,
            @Param("updateTime") LocalDateTime updateTime
    );
}