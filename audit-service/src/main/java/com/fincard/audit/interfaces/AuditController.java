package com.fincard.audit.interfaces;

import com.fincard.audit.domain.AuditEvent;
import com.fincard.audit.domain.AuditEventRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {
    private final AuditEventRepository repository;

    public AuditController(AuditEventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/health")
    public String health() {
        return "audit-service-ok";
    }
}
