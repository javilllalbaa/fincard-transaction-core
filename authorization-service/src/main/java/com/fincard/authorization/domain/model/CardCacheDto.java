package com.fincard.authorization.domain.model;

import java.math.BigDecimal;

public record CardCacheDto(
        Long cardId,
        String cardNumber,
        CardStatus status,
        BigDecimal availableBalance,
        BigDecimal creditLimit
) {
}
