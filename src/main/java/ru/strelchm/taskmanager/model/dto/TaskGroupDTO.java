package ru.strelchm.taskmanager.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Обертка для ответа сервера над коллекцией заданий, содержащая доп. поля статистики
 */
@NoArgsConstructor
@Getter
@Setter
public class TaskGroupDTO {
    private List<TaskDTO> tasks;
    private Long todoTotalTaskCount;
    private Long doneTotalTaskCount;
}
