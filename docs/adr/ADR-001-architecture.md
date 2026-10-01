# ADR-001: Multi-module microservice-oriented architecture

## Context
The platform requires independently deployable authorization and audit capabilities.

## Decision
Use a Maven multi-module repository with `authorization-service` and `audit-service`.
Each service owns its application/domain/infrastructure/interfaces packages.

## Consequences
- Clear service boundaries.
- Independent Docker images and ports.
- Shared build configuration at the root.
- More operational complexity than a monolith.
