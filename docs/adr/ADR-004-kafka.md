# ADR-004: Apache Kafka

## Context
Audit events should be processed asynchronously without coupling the authorization request to audit persistence.

## Decision
Use Kafka as the event backbone. The audit service consumes `fincard.audit.events`.

## Consequences
- Decouples producers and consumers.
- Supports asynchronous processing and replay.
- Requires operational controls for delivery, retries, ordering and retention in production.
