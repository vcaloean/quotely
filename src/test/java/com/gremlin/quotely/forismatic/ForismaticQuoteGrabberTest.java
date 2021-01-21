package com.gremlin.quotely.forismatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gremlin.quotely.quotes.Quote;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ForismaticQuoteGrabberTest {
    private final static String QUOTE_TEXT = "quoteText";
    private final static String QUOTE_AUTHOR = "quoteAuthor";

    private final CloseableHttpClient client = mock(CloseableHttpClient.class);

    @Mock private ObjectMapper mapper;

    @InjectMocks
    private ForismaticQuoteGrabber quoteGrabber = new ForismaticQuoteGrabber("url", QUOTE_TEXT, QUOTE_AUTHOR, client);

    private AutoCloseable mocksCloseable;

    @BeforeEach
    public void setup() {
        mocksCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void teardown() throws Exception {
        mocksCloseable.close();
    }

    @Test
    public void getQuote() throws IOException {
        for (Language language : Language.values()) {
            var expected = new Quote(RandomString.make(), RandomString.make());

            given(client.execute(any(), any(ResponseHandler.class))).willReturn(
                new HashMap<>() {{
                    put(QUOTE_TEXT, expected.getQuote());
                    put(QUOTE_AUTHOR, expected.getAuthor());
                }}
            );

            Quote actual = quoteGrabber.getQuote(language.getText());

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Test
    public void getQuote_badLanguage() {
        assertThrows(UnsupportedOperationException.class, () -> quoteGrabber.getQuote(RandomString.make()));
    }

    @Test
    public void getQuote_clientThrowsException() throws IOException {
        given(client.execute(any(), any(ResponseHandler.class))).willThrow(new IOException());
        assertThat(quoteGrabber.getQuote(Language.ENGLISH.getText())).isEqualTo(null);
    }
}