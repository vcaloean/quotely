package com.gremlin.quotely.quotes;

/**
 * Class that allows modularity in how the quote is grabbed.
 */
public abstract class QuoteGrabber {

    /**
     * Given a language, return a {@link Quote}.
     *
     * @param language A language (case-insensitive)
     * @return a quote
     */
    public abstract Quote getQuote(String language);
}
