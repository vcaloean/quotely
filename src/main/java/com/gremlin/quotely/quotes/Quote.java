package com.gremlin.quotely.quotes;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Simple POJO to hold information about the quote.
 */
@EqualsAndHashCode
@AllArgsConstructor
public class Quote {
    @Getter private final String quote;
    @Getter private final String author;
}
