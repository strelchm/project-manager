package ru.strelchm.taskmanager.model.dbo;

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
public class TaskGroup {
    private List<Task> tasks;
    private Long todoTotalTaskCount;
    private Long doneTotalTaskCount;
}
