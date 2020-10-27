package ru.strelchm.taskmanager.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Базовый класс для сущностей, содержащий общие поля
 */
@Getter
@Setter
@MappedSuperclass
public class BasicEntity {
    @Id
    @GeneratedValue
    @Column(unique = true)
    private UUID id;
    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
