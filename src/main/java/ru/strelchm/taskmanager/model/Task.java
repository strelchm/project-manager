package ru.strelchm.taskmanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Сущность задания
 */
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "tasks")
public class Task extends BasicEntity {
    @Id
    private UUID id;
    @NotNull // не используем @NotBlank, т к дальше при путой строке прокидываем IncorrectNameException
    @Size(min = 0, max = 256)
    private String title;
    @Size(min = 0, max = 256)
    private String description;
    @Enumerated // выбрано именно ORDINAL для масштабирования, т к значение меньше, чем STRING-овое
    @Column(columnDefinition = "smallint")
    private PriorityType priority;
    private boolean done;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;
}
