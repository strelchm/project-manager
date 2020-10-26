package ru.strelchm.taskmanager.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull
    @Size(min = 0, max = 256)
    private String title;
    @JsonIgnore
    @OneToMany(mappedBy = "taskList", fetch = FetchType.LAZY)
    private List<Task> tasks;
}
