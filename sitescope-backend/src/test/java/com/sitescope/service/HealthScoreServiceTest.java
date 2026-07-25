package com.sitescope.service;

import com.sitescope.model.AuditResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthScoreServiceTest {

    private HealthScoreService service;

    @BeforeEach
    void setUp() {
        service = new HealthScoreService();
    }

    @Test
    void testPerfectScore() {
        AuditResult result = new AuditResult(
                "https://example.com",
                200,
                150L,
                "Example Title",
                "Example Description",
                2,
                0,
                500
        );

        int score = service.calculateScore(result);
        assertEquals(100, score);
        assertEquals("Excellent", service.determineStatus(score));
    }

    @Test
    void testPartialScore() {
        AuditResult result = new AuditResult(
                "https://example.com",
                200,
                150L,
                "", // missing title: -15
                "Example Description",
                0, // missing H1: -15
                2, // missing ALT: -15
                150 // word count < 300: -20
        );

        // Score: 20 (reachable) + 15 (meta) = 35
        int score = service.calculateScore(result);
        assertEquals(35, score);
        assertEquals("Poor", service.determineStatus(score));
    }

    @Test
    void testUnreachableWebsiteScore() {
        AuditResult result = new AuditResult(
                "https://example.com",
                500,
                0L,
                "",
                "",
                0,
                0,
                0
        );

        int score = service.calculateScore(result);
        assertEquals(0, score);
        assertEquals("Poor", service.determineStatus(score));
    }
}
