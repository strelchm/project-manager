package ru.strelchm.taskmanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Сущность задания
 */
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Task extends BasicEntity {
    @Id
    private UUID id;
    @NotNull // не используем @NotBlank, т к дальше при путой строке прокидываем IncorrectNameException
    private String title;
    private String description;
    @Enumerated // выбрано именно ORDINAL для масштабирования, т к значение меньше, чем STRING-овое
    @Column(columnDefinition = "smallint")
    private PriorityType priority;
    private boolean done;
}
