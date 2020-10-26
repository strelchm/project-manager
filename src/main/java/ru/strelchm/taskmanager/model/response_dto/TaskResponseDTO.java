package ru.strelchm.taskmanager.model.response_dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.strelchm.taskmanager.model.entity.Task;

import java.util.List;

/**
 * Обертка для ответа сервера над коллекцией заданий, содержащая доп. поля статистики
 */
@NoArgsConstructor
@Getter
@Setter
public class TaskResponseDTO {
    private List<Task> tasks;
    private Long todoTotalTaskCount;
    private Long doneTotalTaskCount;
}
