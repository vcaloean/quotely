package com.gremlin.quotely.quotes;

import lombok.Getter;

public class Quote {
    @Getter private final String quote;
    @Getter private final String author;

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }
}
