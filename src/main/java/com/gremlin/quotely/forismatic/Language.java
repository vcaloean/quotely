package com.gremlin.quotely.forismatic;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Supported languages and html equivalent for Forismatic.
 */
@AllArgsConstructor
public enum Language {
    ENGLISH("English", "en"),
    RUSSIAN("Russian", "ru");

    @Getter private final String text;
    @Getter private final String urlText;

    /**
     * Retrieves Enum equivalent (if exists) of provided language.
     *
     * @param language the language to retrieve (must be fully spelled out)
     * @return the Enum equivalent
     * @throws UnsupportedOperationException if language does not exist
     */
    public static Language valueOfLanguage(String language) {
        return Arrays.stream(Language.values())
            .filter(l -> l.text.equalsIgnoreCase(language))
            .findFirst()
            .orElseThrow(() -> new UnsupportedOperationException("Language \"" + language + "\" is not recognized"));
    }
}
