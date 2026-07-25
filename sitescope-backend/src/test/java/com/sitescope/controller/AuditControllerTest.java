package com.sitescope.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitescope.dto.AuditRequest;
import com.sitescope.dto.AuditResponse;
import com.sitescope.exception.InvalidURLException;
import com.sitescope.service.AuditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditController.class)
class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuditService auditService;

    @Test
    void testRootHealthCheckReturnsOk() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.project").value("SiteScope - Website Health & SEO Analyzer API"));
    }

    @Test
    void testAuditWebsiteSuccess() throws Exception {
        AuditRequest request = new AuditRequest("https://example.com");
        AuditResponse mockResponse = new AuditResponse(
                "https://example.com",
                200,
                120L,
                "Example Title",
                "Example Meta Description",
                1,
                0,
                450,
                100,
                "Excellent",
                List.of()
        );

        when(auditService.performAudit(any(AuditRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/audit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("https://example.com"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.healthScore").value(100))
                .andExpect(jsonPath("$.healthStatus").value("Excellent"));
    }

    @Test
    void testAuditWebsiteBlankUrlReturnsBadRequest() throws Exception {
        AuditRequest request = new AuditRequest("   ");

        mockMvc.perform(post("/api/v1/audit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("URL must not be blank"));
    }

    @Test
    void testAuditWebsiteInvalidUrlThrowsCustomException() throws Exception {
        AuditRequest request = new AuditRequest("ftp://invalid-scheme.com");

        when(auditService.performAudit(any(AuditRequest.class)))
                .thenThrow(new InvalidURLException("Please enter a valid website URL with http:// or https:// scheme."));

        mockMvc.perform(post("/api/v1/audit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Please enter a valid website URL with http:// or https:// scheme."));
    }
}
