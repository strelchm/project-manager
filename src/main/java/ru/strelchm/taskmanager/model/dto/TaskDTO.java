package ru.strelchm.taskmanager.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.strelchm.taskmanager.model.dbo.BasicEntity;
import ru.strelchm.taskmanager.model.dbo.PriorityType;
import ru.strelchm.taskmanager.model.dbo.TaskListDBO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Представление задания
 */
@ApiModel(description = "Представление задания")

@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    @ApiModelProperty(notes = "Название", example = "1", required = true, position = 0)
    @NotNull // не используем @NotBlank, т к дальше при путой строке прокидываем IncorrectNameException
    @Size(min = 0, max = 256)
    private String title;

    @ApiModelProperty(notes = "Описание", example = "1", required = true, position = 1)
    @Size(min = 0, max = 256)
    private String description;

    @ApiModelProperty(notes = "Приоритет", example = "1", required = true, position = 2)
    private PriorityType priority;

    @ApiModelProperty(notes = "Готовность", example = "true", required = true, position = 3)
    private Boolean done;

    @ApiModelProperty(notes = "Список заданий", example = "TODO", required = true, position = 4)
    private TaskListDBO taskList;
}
