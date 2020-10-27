package ru.strelchm.taskmanager.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Сущность задания
 */
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "tasks")
public class Task extends BasicEntity {
    @NotNull // не используем @NotBlank, т к дальше при путой строке прокидываем IncorrectNameException
    @Size(min = 0, max = 256)
    @Column(unique = true)
    private String title;
    @Size(min = 0, max = 256)
    private String description;
    @Enumerated(EnumType.STRING)
    private PriorityType priority;
    private Boolean done;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;
}
