package com.fincard.audit.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fincard.audit.domain.AuditEvent;
import com.fincard.audit.domain.AuditEventRepository;
import com.fincard.audit.infrastructure.dto.AuditMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

@Component
public class AuditKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(AuditKafkaConsumer.class);
    private final AuditEventRepository repository;
    private final ObjectMapper objectMapper;

    public AuditKafkaConsumer(AuditEventRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "transaction-events", groupId = "audit-service")
    public void consume(String message) throws JsonProcessingException {
        System.out.println("data" + message);

        log.info("Mensaje plano recibido desde Kafka: {}", message);

        Map<String, Object> payload = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {});

        AuditMessage auditMessage = objectMapper.readValue(message, AuditMessage.class);
        log.info("Mensaje plano recibido desde Kafka: {}", auditMessage);
        repository.save(new AuditEvent(
                auditMessage.cardId(),
                "Audit",
                "Generico",
                payload
        ));
    }
}
