package ru.strelchm.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RuntimeException, возникающий при анализе запроса клиента в случае:
 * id - идентификатор (@PathVariable) не равен id полученного объекта (@RequestBody)
 * Если id в полученном объекте - null, то исключение не выбрасывается
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DifferentRequestIdException extends RuntimeException {
    public DifferentRequestIdException(String message) {
        super(message);
    }
}
