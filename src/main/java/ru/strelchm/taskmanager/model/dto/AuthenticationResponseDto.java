package ru.strelchm.taskmanager.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class AuthenticationResponseDto {
    private String jwt;

    public AuthenticationResponseDto(String jwtToken) {
    }
}
