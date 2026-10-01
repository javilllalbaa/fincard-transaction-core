package com.fincard.authorization.domain.repository;

import java.math.BigDecimal;

public interface CardAuthorization {

    boolean authorize(Long cardId, BigDecimal amount);
}
