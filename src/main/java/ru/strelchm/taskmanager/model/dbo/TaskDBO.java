package ru.strelchm.taskmanager.model.dbo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel(description = "Сущность задания")
@Entity
@Table(name = "tasks")
public class TaskDBO extends BasicEntity {
    @ApiModelProperty(notes = "Название", example = "1", required = true, position = 0)
    @Column(unique = true)
    private String title;

    @ApiModelProperty(notes = "Описание", example = "1", required = true, position = 1)
    @Size(min = 0, max = 256)
    private String description;

    @ApiModelProperty(notes = "Приоритет", example = "1", required = true, position = 2)
    @Enumerated(EnumType.STRING)
    private PriorityType priority;

    @ApiModelProperty(notes = "Готовность", example = "1", required = true, position = 3)
    private Boolean done;

    @ApiModelProperty(notes = "Список листа заданий", example = "1", required = true, position = 4)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_list_id")
    private TaskListDBO taskList;
}
