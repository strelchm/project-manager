package ru.strelchm.taskmanager.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

/**
 * Обертка для ответа сервера над коллекцией списков заданий, содержащая доп. поля статистики
 */
@ApiModel(description = "Представление списка списков заданий с доп. параметрами для статистики")

@NoArgsConstructor
@Getter
@Setter
public class TaskListGroupDTO {
    private Page<TaskListDTO> taskLists;

    @ApiModelProperty(notes = "Количество незавершенных списков задний", example = "4", required = true, position = 0)
    private Long todoTaskListCount; // стастистика для расширения Pageable

    @ApiModelProperty(notes = "Количество завершенных списков задний", example = "10", required = true, position = 0)
    private Long doneTaskListCount; // стастистика для расширения Pageable
}
