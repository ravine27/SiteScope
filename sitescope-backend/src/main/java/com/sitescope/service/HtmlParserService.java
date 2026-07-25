package com.sitescope.service;

import com.sitescope.util.WordCounter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class HtmlParserService {

    public String extractTitle(Document document) {
        if (document == null) {
            return "";
        }
        String title = document.title();
        return title != null ? title.trim() : "";
    }

    public String extractMetaDescription(Document document) {
        if (document == null) {
            return "";
        }
        Element metaDesc = document.selectFirst("meta[name~=(?i)description]");
        if (metaDesc != null && metaDesc.hasAttr("content")) {
            return metaDesc.attr("content").trim();
        }
        Element ogDesc = document.selectFirst("meta[property=og:description]");
        if (ogDesc != null && ogDesc.hasAttr("content")) {
            return ogDesc.attr("content").trim();
        }
        return "";
    }

    public int countH1Tags(Document document) {
        if (document == null) {
            return 0;
        }
        return document.select("h1").size();
    }

    public int countImagesMissingAlt(Document document) {
        if (document == null) {
            return 0;
        }
        Elements images = document.select("img");
        int missingCount = 0;
        for (Element img : images) {
            if (!img.hasAttr("alt") || img.attr("alt").trim().isEmpty()) {
                missingCount++;
            }
        }
        return missingCount;
    }

    public int calculateWordCount(Document document) {
        if (document == null || document.body() == null) {
            return 0;
        }
        return WordCounter.countWords(document.body().text());
    }
}
