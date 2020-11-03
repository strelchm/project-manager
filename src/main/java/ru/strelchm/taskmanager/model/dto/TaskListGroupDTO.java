package ru.strelchm.taskmanager.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

/**
 * Обертка для ответа сервера над коллекцией списков заданий, содержащая доп. поля статистики
 */
@NoArgsConstructor
@Getter
@Setter
public class TaskListGroupDTO {
    private Page<TaskListDTO> taskLists;
    private Long todoTaskListCount; // стасттистика для расширения Pageable
    private Long doneTaskListCount; // стасттистика для расширения расширение Pageable
}
