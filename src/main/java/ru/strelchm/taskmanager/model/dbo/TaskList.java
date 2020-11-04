package ru.strelchm.taskmanager.model.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность списка заданий
 */
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "task_lists")
public class TaskList extends BasicEntity {
    @Column(unique = true)
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "taskList", fetch = FetchType.LAZY)
    private List<Task> tasks;
}
