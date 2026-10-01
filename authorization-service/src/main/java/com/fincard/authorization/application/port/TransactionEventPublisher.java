package com.fincard.authorization.application.port;

import com.fincard.authorization.domain.model.Transaction;

public interface TransactionEventPublisher {

    void publish(Transaction event, String transactionId);
}
