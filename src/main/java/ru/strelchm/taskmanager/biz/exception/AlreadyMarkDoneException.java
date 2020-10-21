package ru.strelchm.taskmanager.biz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.strelchm.taskmanager.model.Task;

/**
 * RuntimeException, возникающий при попытке установить поле готовности в завершенное при уже завершенном задании
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AlreadyMarkDoneException extends RuntimeException {
    public AlreadyMarkDoneException(Task task) {
        super("Task '" + task.getTitle() + "' is already marked done");
    }
}
