# ADR-003: Redis

## Context
The platform includes a cache requirement and needs low-latency transient storage.

## Decision
Provide Redis as infrastructure and Spring Data Redis integration in the authorization service.

## Consequences
- Enables future token/session/cache strategies.
- Cache invalidation and TTL policies must be defined as the platform evolves.
