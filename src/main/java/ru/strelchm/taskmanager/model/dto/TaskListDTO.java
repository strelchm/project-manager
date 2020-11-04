package ru.strelchm.taskmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.strelchm.taskmanager.model.dbo.Task;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Представление входного списка заданий
 */
@NoArgsConstructor
@Getter
@Setter
public class TaskListDTO {
    @ApiModelProperty(notes = "Идентификатор", example = "08827999-f46e-4045-9c2a-59fad8db2cb3", required = true)
    private UUID id;

    @ApiModelProperty(notes = "Время создания", example = "2020-11-04T16:31:55.492", required = true)
    private LocalDateTime createTime;

    @ApiModelProperty(notes = "Время изменения", example = "2020-11-04T16:31:55.492", required = true)
    private LocalDateTime updateTime;

    @ApiModelProperty(notes = "Название", example = "Some title...", required = true, position = 0)
    @NotNull(message = "Task list title is null")
    @Size(min = 0, max = 256, message = "Wrong string size")
    private String title;

    //    @ApiModelProperty(notes = "Список листа заданий", example = "1", required = true, position = 4)
    @JsonIgnore
    private List<Task> tasks;
}
