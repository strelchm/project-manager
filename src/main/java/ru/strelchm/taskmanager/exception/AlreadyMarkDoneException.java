package ru.strelchm.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.strelchm.taskmanager.model.dbo.TaskDBO;

/**
 * RuntimeException, возникающий при попытке установить поле готовности в завершенное при уже завершенном задании
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AlreadyMarkDoneException extends RuntimeException {
    public AlreadyMarkDoneException(TaskDBO task) {
        super("Task '" + task.getTitle() + "' is already marked done");
    }

    public AlreadyMarkDoneException(String message) {
        super(message);
    }
}