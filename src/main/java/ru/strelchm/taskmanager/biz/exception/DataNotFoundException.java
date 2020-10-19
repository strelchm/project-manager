package ru.strelchm.taskmanager.biz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Отсутствие объекта в БД (напр., при поиске по id)
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String s) {
        super(s);
    }
}
