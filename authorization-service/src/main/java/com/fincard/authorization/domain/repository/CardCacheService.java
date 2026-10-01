package com.fincard.authorization.domain.repository;

import com.fincard.authorization.domain.model.CardCacheDto;

import java.util.Optional;

public interface CardCacheService {

    Optional<CardCacheDto> get(String cardNumber);
    void put(String cardNumber, CardCacheDto card);
    void evict(String cardNumber);

}