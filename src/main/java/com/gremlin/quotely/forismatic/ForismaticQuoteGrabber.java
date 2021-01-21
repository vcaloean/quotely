package com.gremlin.quotely.forismatic;

import static java.util.stream.Collectors.joining;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gremlin.quotely.quotes.Quote;
import com.gremlin.quotely.quotes.QuoteGrabber;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Specific {@link QuoteGrabber} for Forismatic.
 */
public class ForismaticQuoteGrabber extends QuoteGrabber {
    private final URI quoteUrl;
    private final String quoteText;
    private final String quoteAuthor;
    private final CloseableHttpClient client;

    @Autowired private ObjectMapper mapper;

    /**
     * Constructor for {@link ForismaticQuoteGrabber}.
     *
     * @param quoteUrl the API URL for Forismatic to retrieve quote
     * @param quoteText the key in the response map to get quote
     * @param quoteAuthor the key in the response map to get author
     * @param client an HTTP client in order to make the API call
     */
    public ForismaticQuoteGrabber(String quoteUrl, String quoteText, String quoteAuthor, CloseableHttpClient client) {
        this.quoteUrl = URI.create(quoteUrl);
        this.quoteText = quoteText;
        this.quoteAuthor = quoteAuthor;
        this.client = client;
    }

    @Override
    public Quote getQuote(String language) {
        var l = Language.valueOfLanguage(language);

        Map<String, String> bodyValues = new HashMap<>() {{
            put("method", "getQuote");
            put("format", "json");
            put("lang", l.getUrlText());
        }};

        var requestBody = bodyValues.keySet().stream()
            .map(key -> key + "=" + URLEncoder.encode(bodyValues.get(key), StandardCharsets.UTF_8))
            .collect(joining("&"));

        Map<String, String> response;
        try (client) {
            response = client.execute(new HttpPost(quoteUrl + "?" + requestBody), httpResponse ->
                mapper.readValue(httpResponse.getEntity().getContent(), Map.class)
            );
        } catch (IOException e) {
            System.out.println("Error in calling url: " + e);
            return null;
        }

        return new Quote(response.get(quoteText), response.get(quoteAuthor));
    }
}
