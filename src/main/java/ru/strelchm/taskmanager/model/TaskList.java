package ru.strelchm.taskmanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * Сущность списка задания
 */
@NoArgsConstructor
@Getter
@Setter

@Entity
public class TaskList extends BasicEntity {
    @Id
    private UUID id;
    @NotNull
    private String title;
    private List<Task> tasks;
}
