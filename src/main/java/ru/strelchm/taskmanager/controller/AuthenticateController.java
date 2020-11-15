package ru.strelchm.taskmanager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.taskmanager.config.JwtUtil;
import ru.strelchm.taskmanager.model.dto.AuthenticationRequestDto;
import ru.strelchm.taskmanager.model.dto.AuthenticationResponseDto;
import ru.strelchm.taskmanager.service.impl.CustomUserDetailsService;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * REST-контроллер для ресурсов аутентификации
 */
@RestController
@RequestMapping("/authenticate")
@Api(description = "REST-контроллер для процесса аутентификации")
public class AuthenticateController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticateController(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @ApiOperation("Создание задания")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> createAuthToken(@RequestBody @Valid AuthenticationRequestDto authRequest) {
        final UserDetails userDetails;
        String jwtToken;

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUserName(),
                authRequest.getPassword())
        );

        userDetails = userDetailsService.loadUserByUsername(authRequest.getUserName());

        jwtToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponseDto(jwtToken));
    }

    private static final String BAD_CREDENTIALS_ERROR_MESSAGE = "Username or password is incorrect";

    /**
     * Обработка рантайм-ошибки "BadCredentialsException"
     *
     * @param ex - 400 код ошибки
     * @return коллекция параметров ошибочного ответа
     */
    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = BAD_CREDENTIALS_ERROR_MESSAGE)
    public HashMap<String, String> handleBadRequestExceptions(Exception ex) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", BAD_CREDENTIALS_ERROR_MESSAGE);
        response.put("error", ex.getClass().getSimpleName());
        return response;
    }
}
