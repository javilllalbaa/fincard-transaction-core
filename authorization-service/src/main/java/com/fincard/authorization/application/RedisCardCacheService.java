package com.fincard.authorization.application;

import com.fincard.authorization.domain.model.CardCacheDto;
import com.fincard.authorization.domain.repository.CardCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisCardCacheService implements CardCacheService {

    @Autowired
    private final RedisTemplate<String, CardCacheDto> redisTemplate;

    private static final String PREFIX = "card:";

    @Override
    public Optional<CardCacheDto> get(String cardNumber) {

        String key = PREFIX + cardNumber;

        CardCacheDto card = redisTemplate.opsForValue().get(key);

        return Optional.ofNullable(card);
    }

    @Override
    public void put(String cardNumber, CardCacheDto card) {

        String key = PREFIX + cardNumber;

        redisTemplate.opsForValue()
                .set(key, card, Duration.ofMinutes(5));
    }

    @Override
    public void evict(String cardNumber) {

        redisTemplate.delete(PREFIX + cardNumber);
    }
}