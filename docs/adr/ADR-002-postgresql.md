# ADR-002: PostgreSQL

## Context
Authentication and audit records require transactional relational persistence.

## Decision
Use PostgreSQL 16 with Spring Data JPA and SQL initialization scripts.

## Consequences
- Strong relational consistency.
- SQL indexes and stored function examples are versioned.
- Database schema becomes an explicit deployment dependency.
