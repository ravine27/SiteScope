package com.sitescope.validator;

import com.sitescope.exception.InvalidURLException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class UrlValidator {

    public void validate(String urlString) {
        if (urlString == null || urlString.isBlank()) {
            throw new InvalidURLException("Please enter a valid website URL.");
        }

        String trimmedUrl = urlString.trim();

        if (!trimmedUrl.startsWith("http://") && !trimmedUrl.startsWith("https://")) {
            throw new InvalidURLException("Please enter a valid website URL with http:// or https:// scheme.");
        }

        try {
            URI uri = new URI(trimmedUrl);
            String host = uri.getHost();

            if (host == null || host.isBlank()) {
                throw new InvalidURLException("Please enter a valid website URL containing a valid host name.");
            }

            // Reject loopback and localhost
            String lowerHost = host.toLowerCase();
            if (lowerHost.equals("localhost") || lowerHost.equals("127.0.0.1") || lowerHost.equals("0.0.0.0") || lowerHost.endsWith(".local")) {
                throw new InvalidURLException("Localhost and loopback addresses cannot be audited.");
            }

            // Must have TLD or valid domain dot
            if (!lowerHost.contains(".") && !lowerHost.equals("localhost")) {
                throw new InvalidURLException("Please enter a valid fully-qualified website domain name.");
            }

        } catch (URISyntaxException e) {
            throw new InvalidURLException("Please enter a valid website URL structure.");
        }
    }
}
