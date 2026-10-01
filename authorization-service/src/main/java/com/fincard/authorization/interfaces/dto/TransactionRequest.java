package com.fincard.authorization.interfaces.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record TransactionRequest(
        @NotBlank(message = "El número de tarjeta no puede estar vacío")
        @Pattern(regexp = "^[0-9]+$", message = "El número de tarjeta debe contener solo números")
        String cardNumber,

        @NotNull(message = "El monto no puede ser nulo")
        @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
        BigDecimal amount,

        @NotBlank(message = "La moneda no puede estar vacía")
        @Pattern(regexp = "^[A-Z]{3}$", message = "La moneda debe tener un formato ISO de 3 letras (ej. USD)")
        String currency
) {}