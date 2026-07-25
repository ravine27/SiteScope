package com.sitescope.model;

public class Recommendation {

    private String severity; // WARNING, INFO, CRITICAL
    private String message;

    public Recommendation() {
    }

    public Recommendation(String severity, String message) {
        this.severity = severity;
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
