package com.fincard.audit.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuditMessage(
        Long id,
        Long cardId,
        Double amount, // O BigDecimal si manejas precisión financiera
        String currency,
        String status,
        String authorizationCode,
        LocalDateTime createdAt
) {}