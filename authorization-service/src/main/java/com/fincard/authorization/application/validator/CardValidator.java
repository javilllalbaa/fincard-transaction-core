package com.fincard.authorization.application.validator;

import com.fincard.authorization.domain.model.CardCacheDto;
import com.fincard.authorization.domain.model.CardStatus;
import com.fincard.authorization.infrastructure.exception.InvalidCardStatusException;
import com.fincard.authorization.interfaces.dto.TransactionRequest;
import org.springframework.stereotype.Component;

@Component
public class CardValidator {

    public void validate(CardCacheDto card, TransactionRequest request) {

        validateStatus(card, request);
    }

    private void validateStatus(CardCacheDto card, TransactionRequest request) {

        if (CardStatus.BLOCKED.equals(card.status())) {
            throw new InvalidCardStatusException("Tarjeta Bloqueada");
        }

        if (!CardStatus.ACTIVE.equals(card.status())) {
            throw new InvalidCardStatusException("Error con el estado de la tarjeta");
        }

        if (card.availableBalance().compareTo(request.amount()) < 0) {
            throw new InvalidCardStatusException("Saldo insuficiente");
        }

    }

}
