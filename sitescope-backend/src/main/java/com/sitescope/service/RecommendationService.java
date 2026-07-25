package com.sitescope.service;

import com.sitescope.model.AuditResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    public List<String> generateRecommendations(AuditResult result) {
        List<String> recommendations = new ArrayList<>();

        if (result == null) {
            return recommendations;
        }

        if (result.getTitle() == null || result.getTitle().isBlank()) {
            recommendations.add("Add a descriptive page title.");
        }

        if (result.getMetaDescription() == null || result.getMetaDescription().isBlank()) {
            recommendations.add("Add a meta description for better SEO.");
        }

        if (result.getH1Count() == 0) {
            recommendations.add("Include at least one H1 heading.");
        }

        if (result.getImagesMissingAlt() > 0) {
            recommendations.add("Add ALT text to " + result.getImagesMissingAlt() + " image(s) for accessibility.");
        }

        if (result.getWordCount() < 300) {
            recommendations.add("Increase content depth (currently " + result.getWordCount() + " words).");
        }

        if (result.getResponseTime() > 2000) {
            recommendations.add("Improve page performance (response time " + result.getResponseTime() + " ms).");
        }

        return recommendations;
    }
}
