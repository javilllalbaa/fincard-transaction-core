package com.fincard.authorization.domain.repository;

import com.fincard.authorization.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
