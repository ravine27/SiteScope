package com.sitescope.controller;

import com.sitescope.dto.AuditRequest;
import com.sitescope.dto.AuditResponse;
import com.sitescope.service.AuditService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping("/audit")
    public ResponseEntity<AuditResponse> auditWebsite(@Valid @RequestBody AuditRequest request) {
        AuditResponse response = auditService.performAudit(request);
        return ResponseEntity.ok(response);
    }
}
