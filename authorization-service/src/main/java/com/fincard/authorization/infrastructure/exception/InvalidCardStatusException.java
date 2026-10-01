package com.fincard.authorization.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // Esto fuerza el código HTTP 409
public class InvalidCardStatusException extends RuntimeException {
    public InvalidCardStatusException(String message) {
        super(message);
    }
}
