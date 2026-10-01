package com.fincard.authorization.infrastructure.adapter.out;

import com.fincard.authorization.domain.repository.CardAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class PostgresCardAuthorizationAdapter
        implements CardAuthorization {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean authorize(
            Long cardId,
            BigDecimal amount) {

        String sql = """
                SELECT authorize_transaction(?, ?)
                """;

        Boolean result = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                cardId,
                amount
        );

        return Boolean.TRUE.equals(result);
    }
}