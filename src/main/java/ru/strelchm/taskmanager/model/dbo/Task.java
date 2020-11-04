package ru.strelchm.taskmanager.model.dbo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(unique = true)
    private String title;

    @Size(min = 0, max = 256)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskPriorityType priority;

    private Boolean done;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_list_id")
    private TaskList taskList;
}
