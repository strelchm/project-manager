package ru.strelchm.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Невозможно распознать приоритетность по входному значению
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TaskPriorityUnrecognizeException extends RuntimeException {
    public TaskPriorityUnrecognizeException(String s) {
        super(s);
    }
}