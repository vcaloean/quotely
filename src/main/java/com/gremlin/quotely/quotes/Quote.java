package com.gremlin.quotely.quotes;

import java.util.Objects;
import lombok.Getter;

public class Quote {
    @Getter private final String quote;
    @Getter private final String author;

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quote quote1 = (Quote) o;
        return Objects.equals(quote, quote1.quote) && Objects.equals(author, quote1.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quote, author);
    }
}
