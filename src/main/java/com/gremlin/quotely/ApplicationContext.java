package com.gremlin.quotely;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gremlin.quotely.forismatic.ForismaticQuoteGrabber;
import com.gremlin.quotely.quotes.QuoteGrabber;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContext {
    @Value("${forismatic.url}")
    private String forismaticUrl;

    @Value("${forismatic.quote.text}")
    private String forismaticQuoteText;

    @Value("${forismatic.quote.author}")
    private String forismaticQuoteAuthor;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public QuoteGrabber quoteGrabber() {
        return new ForismaticQuoteGrabber(
            forismaticUrl,
            forismaticQuoteText,
            forismaticQuoteAuthor,
            HttpClients.createDefault()
        );
    }
}
