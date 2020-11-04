package ru.strelchm.taskmanager.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Обертка для ответа сервера над коллекцией заданий, содержащая доп. поля статистики
 */
@ApiModel(description = "Представление списка заданий с доп. параметрами для статистики")

@NoArgsConstructor
@Getter
@Setter
public class TaskGroupDTO {
    private List<TaskDTO> tasks;

    @ApiModelProperty(notes = "Количество незавершенных заданий", example = "4", required = true, position = 0)
    private Long todoTotalTaskCount;

    @ApiModelProperty(notes = "Количество завершенных заданий", example = "10", required = true, position = 0)
    private Long doneTotalTaskCount;
}
