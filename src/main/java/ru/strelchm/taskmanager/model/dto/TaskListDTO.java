package ru.strelchm.taskmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.strelchm.taskmanager.model.dbo.BasicEntity;
import ru.strelchm.taskmanager.model.dbo.TaskDBO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Представление входного списка заданий
 */
@NoArgsConstructor
@Getter
@Setter
public class TaskListDTO {
    @ApiModelProperty(notes = "Название", example = "1", required = true, position = 0)
    @NotNull
    @Size(min = 0, max = 256)
    private String title;

    @JsonIgnore
    private List<TaskDBO> tasks;
}
