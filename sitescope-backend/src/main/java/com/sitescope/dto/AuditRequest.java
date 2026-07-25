package com.sitescope.dto;

import jakarta.validation.constraints.NotBlank;

public class AuditRequest {

    @NotBlank(message = "URL must not be blank")
    private String url;

    public AuditRequest() {
    }

    public AuditRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
