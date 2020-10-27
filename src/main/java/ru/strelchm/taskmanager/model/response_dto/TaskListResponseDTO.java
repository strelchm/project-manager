package ru.strelchm.taskmanager.model.response_dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import ru.strelchm.taskmanager.model.entity.TaskList;

/**
 * Обертка для ответа сервера над коллекцией списков заданий, содержащая доп. поля статистики
 */
@NoArgsConstructor
@Getter
@Setter
public class TaskListResponseDTO {
    private Page<TaskList> taskLists;
    private Long todoTaskListCount; // стасттистика для расширения Pageable
    private Long doneTaskListCount; // стасттистика для расширения расширение Pageable
}
