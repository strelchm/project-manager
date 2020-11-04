package ru.strelchm.taskmanager.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.strelchm.taskmanager.model.dbo.TaskList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Представление задания
 */
@ApiModel(description = "Представление задания")

@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    @ApiModelProperty(notes = "Идентификатор", example = "08827999-f46e-4045-9c2a-59fad8db2cb3", required = true)
    private UUID id;

    @ApiModelProperty(notes = "Название", example = "2020-11-04T16:31:55.492", required = true)
    private LocalDateTime createTime;

    @ApiModelProperty(notes = "Название", example = "2020-11-04T16:31:55.492", required = true)
    private LocalDateTime updateTime;

    @ApiModelProperty(notes = "Название", example = "Some title...", required = true)
    @NotNull(message = "Task list title is null")
    // не используем @NotBlank, т к дальше при путой строке прокидываем IncorrectNameException
    @Size(min = 0, max = 256, message = "Wrong string size")
    private String title;

    @ApiModelProperty(notes = "Описание", example = "1", required = true, position = 1)
    @Size(min = 0, max = 256, message = "Wrong string size")
    private String description;

    @ApiModelProperty(notes = "Приоритет. Целочисленное значение, которое ограничено вариантами: {LOWEST = 1, LOW = 2, MIDDLE = 3, HIGH = 4, HIGHEST = 5}", example = "1", required = true, position = 2)
    private Integer priority;

    @ApiModelProperty(notes = "Готовность", example = "true", required = true, position = 3)
    private Boolean done;

    @ApiModelProperty(notes = "Список заданий", example = "TODO", required = true, position = 4)
    private TaskList taskList;
}