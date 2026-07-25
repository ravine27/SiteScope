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

    @org.springframework.web.bind.annotation.GetMapping("/")
    public ResponseEntity<java.util.Map<String, String>> rootHealthCheck() {
        java.util.Map<String, String> status = new java.util.HashMap<>();
        status.put("project", "SiteScope - Website Health & SEO Analyzer API");
        status.put("status", "UP");
        status.put("auditEndpoint", "POST /api/v1/audit");
        return ResponseEntity.ok(status);
    }

    @PostMapping("/api/v1/audit")
    public ResponseEntity<AuditResponse> auditWebsite(@Valid @RequestBody AuditRequest request) {
        AuditResponse response = auditService.performAudit(request);
        return ResponseEntity.ok(response);
    }
}
