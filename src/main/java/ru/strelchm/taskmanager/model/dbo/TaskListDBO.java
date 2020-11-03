package ru.strelchm.taskmanager.model.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Сущность списка заданий
 */
@NoArgsConstructor
@Getter
@Setter

@ApiModel(description = "Сущность списка заданий")
@Entity
@Table(name = "task_lists")
public class TaskListDBO extends BasicEntity {
    @ApiModelProperty(notes = "Название", example = "1", required = true, position = 0)
    @Column(unique = true)
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "taskList", fetch = FetchType.LAZY)
    private List<TaskDBO> tasks;
}
