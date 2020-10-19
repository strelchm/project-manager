package ru.strelchm.taskmanager.biz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.strelchm.taskmanager.model.Task;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AlreadyMarkDoneException extends RuntimeException {
    public AlreadyMarkDoneException(Task task) {
        super("Task '" + task.getTitle() + "' is already marked done");
    }
}
