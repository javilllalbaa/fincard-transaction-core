package com.fincard.authorization.application;

import com.fincard.authorization.application.port.TransactionEventPublisher;
import com.fincard.authorization.application.validator.CardValidator;
import com.fincard.authorization.domain.model.Card;
import com.fincard.authorization.domain.model.CardCacheDto;
import com.fincard.authorization.domain.model.Transaction;
import com.fincard.authorization.domain.model.TransactionStatus;
import com.fincard.authorization.domain.repository.CardAuthorization;
import com.fincard.authorization.domain.repository.CardCacheService;
import com.fincard.authorization.domain.repository.CardRepository;
import com.fincard.authorization.domain.repository.TransactionRepository;
import com.fincard.authorization.infrastructure.exception.CardNotFoundException;
import com.fincard.authorization.interfaces.dto.TransactionRequest;
import com.fincard.authorization.interfaces.dto.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionsService {

    private final CardRepository cardRepository;
    private final CardCacheService cardCacheService;
    private final CardValidator cardValidator;
    private final CardAuthorization cardAuthorization;
    private final TransactionRepository transactionRepository;
    private final TransactionEventPublisher eventPublisher;

    public TransactionsService(CardRepository cardRepository, CardCacheService cardCacheService, CardValidator cardValidator, CardAuthorization cardAuthorization, TransactionRepository transactionRepository, TransactionEventPublisher eventPublisher) {
        this.cardRepository = cardRepository;
        this.cardCacheService = cardCacheService;
        this.cardValidator = cardValidator;
        this.cardAuthorization = cardAuthorization;
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }

    public TransactionResponse transaction(TransactionRequest request, String transactionId) {

        try {

            CardCacheDto card = getCardCache(request);
            Transaction transaction = new Transaction();
            cardValidator.validate(card, request);

            boolean authorized =
                    cardAuthorization.authorize(
                            card.cardId(),
                            request.amount()
                    );

            if (!authorized) {

                transaction.setCardId(card.cardId());
                transaction.setAmount(request.amount()); // Monto usando BigDecimal
                transaction.setStatus(TransactionStatus.DECLINED);
                transaction.setCurrency(request.currency());
                transaction.setAuthorizationCode(UUID.randomUUID().toString().substring(0, 10));
                transactionRepository.save(transaction);
            }

            transaction.setCardId(card.cardId());
            transaction.setAmount(request.amount()); // Monto usando BigDecimal
            transaction.setStatus(TransactionStatus.APPROVED);
            transaction.setCurrency(request.currency());
            transaction.setAuthorizationCode(UUID.randomUUID().toString().substring(0, 10));
            transactionRepository.save(transaction);
            cardCacheService.evict(request.cardNumber());
            cardCacheService.put(
                    request.cardNumber(),
                    new CardCacheDto(
                            card.cardId(),
                            card.cardNumber(),
                            card.status(),
                            card.availableBalance().subtract(request.amount()),
                            card.creditLimit()
                    )
            );

            eventPublisher.publish(transaction, transactionId);

            return new TransactionResponse(
                    transactionId,
                    transaction.getStatus(),
                    transaction.getAuthorizationCode(),
                    transaction.getAmount(),
                    transaction.getCurrency()

            );

        } catch (Exception e) {
            throw e;
        }

    }

    private CardCacheDto toCacheDto(Card card) {

        return new CardCacheDto(
                card.getId(),
                card.getCardNumber(),
                card.getStatus(),
                card.getAvailableBalance(),
                card.getCreditLimit()
        );
    }

    private CardCacheDto getCardCache(TransactionRequest request) {
        try {

            CardCacheDto data;
            Optional<CardCacheDto> cached = cardCacheService.get(request.cardNumber());

            if (cached.isPresent()) {
                data = cached.get();
            } else {
                var card = cardRepository.findByCardNumber(request.cardNumber()).orElseThrow(() -> new CardNotFoundException("Número de cuenta no existe"));
                data = toCacheDto(card);
                cardCacheService.put(request.cardNumber(), data);
            }

            return data;
        } catch (CardNotFoundException e) {
            throw e;
        }
    }

}
