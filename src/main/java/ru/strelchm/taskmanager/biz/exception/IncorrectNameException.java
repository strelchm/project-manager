package ru.strelchm.taskmanager.biz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Неверное имя REST-ресурса
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IncorrectNameException extends RuntimeException {
    public IncorrectNameException(String message) {
        super(message);
    }
}
