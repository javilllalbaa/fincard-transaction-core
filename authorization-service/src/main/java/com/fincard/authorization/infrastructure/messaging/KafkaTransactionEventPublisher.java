package com.fincard.authorization.infrastructure.messaging;

import com.fincard.authorization.application.port.TransactionEventPublisher;
import com.fincard.authorization.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaTransactionEventPublisher
        implements TransactionEventPublisher {

    private static final String TOPIC = "transaction-events";

    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    @Override
    public void publish(Transaction event, String transactionId) {

        kafkaTemplate.send(
                TOPIC,
                transactionId,
                event
        );
    }
}
