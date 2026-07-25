package com.sitescope.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HtmlParserServiceTest {

    private HtmlParserService service;

    @BeforeEach
    void setUp() {
        service = new HtmlParserService();
    }

    @Test
    void testExtractTitle() {
        String html = "<html><head><title> Test Page Title </title></head><body></body></html>";
        Document doc = Jsoup.parse(html);
        assertEquals("Test Page Title", service.extractTitle(doc));
    }

    @Test
    void testExtractMetaDescription() {
        String html = "<html><head><meta name=\"description\" content=\"This is a test description.\"></head><body></body></html>";
        Document doc = Jsoup.parse(html);
        assertEquals("This is a test description.", service.extractMetaDescription(doc));
    }

    @Test
    void testCountH1Tags() {
        String html = "<html><body><h1>Header 1</h1><h1>Header 2</h1></body></html>";
        Document doc = Jsoup.parse(html);
        assertEquals(2, service.countH1Tags(doc));
    }

    @Test
    void testCountImagesMissingAlt() {
        String html = "<html><body>" +
                "<img src=\"1.jpg\" alt=\"Valid ALT\">" +
                "<img src=\"2.jpg\">" +
                "<img src=\"3.jpg\" alt=\" \">" +
                "</body></html>";
        Document doc = Jsoup.parse(html);
        assertEquals(2, service.countImagesMissingAlt(doc));
    }

    @Test
    void testCalculateWordCount() {
        String html = "<html><body><p>Hello world from SiteScope analyzer!</p></body></html>";
        Document doc = Jsoup.parse(html);
        assertEquals(5, service.calculateWordCount(doc));
    }
}
