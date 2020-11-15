package ru.strelchm.taskmanager.model.dto;

import lombok.Data;

/**
 * DTO для запроса аутентификации
 */
@Data
public class AuthenticationRequestDto {
    private String userName;
    private String password;
}
