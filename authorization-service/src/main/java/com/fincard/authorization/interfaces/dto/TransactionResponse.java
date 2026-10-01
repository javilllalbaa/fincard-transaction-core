package com.fincard.authorization.interfaces.dto;

import com.fincard.authorization.domain.model.TransactionStatus;

import java.math.BigDecimal;

public record TransactionResponse(
        String transactionId,
        TransactionStatus status,
        String authorizationCode,
        BigDecimal amount,
        String currency
) {}