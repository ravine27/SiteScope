package com.sitescope.service;

import com.sitescope.dto.AuditRequest;
import com.sitescope.dto.AuditResponse;
import com.sitescope.exception.InvalidURLException;
import com.sitescope.exception.UnsupportedContentException;
import com.sitescope.model.AuditResult;
import com.sitescope.validator.UrlValidator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

@Service
public class AuditService {

    private static final Logger logger = LoggerFactory.getLogger(AuditService.class);

    private final UrlValidator urlValidator;
    private final HtmlParserService htmlParserService;
    private final HealthScoreService healthScoreService;
    private final RecommendationService recommendationService;

    @Value("${sitescope.audit.timeout-ms:10000}")
    private int timeoutMs;

    @Value("${sitescope.audit.user-agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) SiteScope/1.0 Website Health Analyzer}")
    private String userAgent;

    public AuditService(UrlValidator urlValidator,
                        HtmlParserService htmlParserService,
                        HealthScoreService healthScoreService,
                        RecommendationService recommendationService) {
        this.urlValidator = urlValidator;
        this.htmlParserService = htmlParserService;
        this.healthScoreService = healthScoreService;
        this.recommendationService = recommendationService;
    }

    public AuditResponse performAudit(AuditRequest request) {
        String url = request.getUrl();
        urlValidator.validate(url);

        long startTime = System.currentTimeMillis();
        Connection.Response response;
        Document document;

        try {
            logger.info("Executing audit for URL: {}", url);
            Connection connection = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .timeout(timeoutMs)
                    .followRedirects(true)
                    .ignoreHttpErrors(true);

            response = connection.execute();
            long responseTime = System.currentTimeMillis() - startTime;

            String contentType = response.contentType();
            logger.info("Fetched URL with HTTP status {} and content-type: {}", response.statusCode(), contentType);

            if (contentType != null && !contentType.toLowerCase().contains("html") && !contentType.toLowerCase().contains("xml")) {
                throw new UnsupportedContentException("This URL does not point to an HTML webpage (Content-Type: " + contentType + ").");
            }

            document = response.parse();

            String title = htmlParserService.extractTitle(document);
            String metaDescription = htmlParserService.extractMetaDescription(document);
            int h1Count = htmlParserService.countH1Tags(document);
            int imagesMissingAlt = htmlParserService.countImagesMissingAlt(document);
            int wordCount = htmlParserService.calculateWordCount(document);

            AuditResult auditResult = new AuditResult(
                    url,
                    response.statusCode(),
                    responseTime,
                    title,
                    metaDescription,
                    h1Count,
                    imagesMissingAlt,
                    wordCount
            );

            int healthScore = healthScoreService.calculateScore(auditResult);
            String healthStatus = healthScoreService.determineStatus(healthScore);
            List<String> recommendations = recommendationService.generateRecommendations(auditResult);

            return new AuditResponse(
                    url,
                    response.statusCode(),
                    responseTime,
                    title,
                    metaDescription,
                    h1Count,
                    imagesMissingAlt,
                    wordCount,
                    healthScore,
                    healthStatus,
                    recommendations
            );

        } catch (SocketTimeoutException e) {
            logger.error("Timeout fetching URL: {}", url, e);
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            logger.error("Unknown host: {}", url, e);
            throw new InvalidURLException("Unable to resolve host domain for: " + url);
        } catch (UnsupportedContentException | InvalidURLException e) {
            throw e;
        } catch (IOException e) {
            logger.error("IO Exception during audit of URL: {}", url, e);
            throw new InvalidURLException("Failed to connect or download page from: " + url + " (" + e.getMessage() + ")");
        }
    }
}
