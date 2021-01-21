package com.gremlin.quotely;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gremlin.quotely.forismatic.ForismaticQuoteGrabber;
import com.gremlin.quotely.quotes.QuoteGrabber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContext {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public QuoteGrabber quoteGrabber() {
        return new ForismaticQuoteGrabber("http://api.forismatic.com/api/1.0/", "quoteText", "quoteAuthor");
    }
}
