package com.sitescope.service;

import com.sitescope.model.AuditResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecommendationServiceTest {

    private RecommendationService service;

    @BeforeEach
    void setUp() {
        service = new RecommendationService();
    }

    @Test
    void testNoRecommendationsForPerfectWebsite() {
        AuditResult result = new AuditResult(
                "https://example.com",
                200,
                300L,
                "Title",
                "Description",
                1,
                0,
                400
        );

        List<String> recommendations = service.generateRecommendations(result);
        assertTrue(recommendations.isEmpty());
    }

    @Test
    void testAllRecommendationsTriggered() {
        AuditResult result = new AuditResult(
                "https://example.com",
                200,
                2500L, // response time > 2000
                "",    // no title
                "",    // no meta
                0,     // no H1
                3,     // 3 missing ALT
                100    // word count < 300
        );

        List<String> recommendations = service.generateRecommendations(result);
        assertEquals(6, recommendations.size());
        assertTrue(recommendations.contains("Add a descriptive page title."));
        assertTrue(recommendations.contains("Add a meta description for better SEO."));
        assertTrue(recommendations.contains("Include at least one H1 heading."));
        assertTrue(recommendations.contains("Add ALT text to 3 image(s) for accessibility."));
        assertTrue(recommendations.contains("Increase content depth (currently 100 words)."));
        assertTrue(recommendations.contains("Improve page performance (response time 2500 ms)."));
    }
}
