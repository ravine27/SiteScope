package com.sitescope.dto;

import java.util.List;

public class AuditResponse {

    private String url;
    private Integer status;
    private Long responseTime;
    private String title;
    private String metaDescription;
    private Integer h1Count;
    private Integer imagesMissingAlt;
    private Integer wordCount;
    private Integer healthScore;
    private String healthStatus;
    private List<String> recommendations;

    public AuditResponse() {
    }

    public AuditResponse(String url, Integer status, Long responseTime, String title,
                         String metaDescription, Integer h1Count, Integer imagesMissingAlt,
                         Integer wordCount, Integer healthScore, String healthStatus,
                         List<String> recommendations) {
        this.url = url;
        this.status = status;
        this.responseTime = responseTime;
        this.title = title;
        this.metaDescription = metaDescription;
        this.h1Count = h1Count;
        this.imagesMissingAlt = imagesMissingAlt;
        this.wordCount = wordCount;
        this.healthScore = healthScore;
        this.healthStatus = healthStatus;
        this.recommendations = recommendations;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public Integer getH1Count() {
        return h1Count;
    }

    public void setH1Count(Integer h1Count) {
        this.h1Count = h1Count;
    }

    public Integer getImagesMissingAlt() {
        return imagesMissingAlt;
    }

    public void setImagesMissingAlt(Integer imagesMissingAlt) {
        this.imagesMissingAlt = imagesMissingAlt;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(Integer healthScore) {
        this.healthScore = healthScore;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
}
