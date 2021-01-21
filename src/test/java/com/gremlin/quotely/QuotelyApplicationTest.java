package com.gremlin.quotely;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.gremlin.quotely.quotes.Quote;
import com.gremlin.quotely.quotes.QuoteGrabber;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QuotelyApplicationTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock private QuoteGrabber quoteGrabber;

    @InjectMocks private QuotelyApplication app;

    private AutoCloseable mocksCloseable;

    @BeforeEach
    public void setup() {;
        System.setOut(new PrintStream(outputStreamCaptor));
        mocksCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void teardown() throws Exception {
        mocksCloseable.close();
        System.setOut(standardOut);
    }

    @Test
    public void run() {
        given(quoteGrabber.getQuote(anyString())).willReturn(new Quote("quote", "author"));

        app.run("english");
        assertThat(outputStreamCaptor.toString()).isNotBlank();
        outputStreamCaptor.reset();

        app.run("russian");
        assertThat(outputStreamCaptor.toString()).isNotBlank();
        outputStreamCaptor.reset();

        // spot checking capitalization
        app.run("English");
        assertThat(outputStreamCaptor.toString()).isNotBlank();
        outputStreamCaptor.reset();

        app.run("EnGlish");
        assertThat(outputStreamCaptor.toString()).isNotBlank();
        outputStreamCaptor.reset();

        app.run("englIsh");
        assertThat(outputStreamCaptor.toString()).isNotBlank();
        outputStreamCaptor.reset();

        app.run("engLisH");
        assertThat(outputStreamCaptor.toString()).isNotBlank();
        outputStreamCaptor.reset();
    }

    @Test
    public void run_tooFewArguments() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> app.run());
    }

    @Test
    public void run_tooManyArguments() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> app.run("1", "2"));
    }
}