package com.sitescope.service;

import com.sitescope.model.AuditResult;
import org.springframework.stereotype.Service;

@Service
public class HealthScoreService {

    public int calculateScore(AuditResult result) {
        if (result == null || result.getStatus() < 200 || result.getStatus() >= 400) {
            return 0;
        }

        int score = 0;

        // Reachable website: +20
        score += 20;

        // Title present: +15
        if (result.getTitle() != null && !result.getTitle().isBlank()) {
            score += 15;
        }

        // Meta description present: +15
        if (result.getMetaDescription() != null && !result.getMetaDescription().isBlank()) {
            score += 15;
        }

        // H1 tag present: +15
        if (result.getH1Count() >= 1) {
            score += 15;
        }

        // Images with ALT: +15 (bonus for all images having ALT text)
        if (result.getImagesMissingAlt() == 0) {
            score += 15;
        }

        // Word count > 300: +20
        if (result.getWordCount() > 300) {
            score += 20;
        }

        return score;
    }

    public String determineStatus(int score) {
        if (score >= 90) {
            return "Excellent";
        } else if (score >= 70) {
            return "Good";
        } else if (score >= 50) {
            return "Needs Improvement";
        } else {
            return "Poor";
        }
    }
}
