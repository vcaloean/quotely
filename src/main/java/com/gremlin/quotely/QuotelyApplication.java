package com.gremlin.quotely;

import com.gremlin.quotely.quotes.Quote;
import com.gremlin.quotely.quotes.QuoteGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The starting point for the "quotely" application.
 */
@SpringBootApplication
public class QuotelyApplication implements CommandLineRunner {
	@Autowired private QuoteGrabber quoteGrabber;

	public static void main(String[] args) {
		SpringApplication.run(QuotelyApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("Arguments cannot be empty");
		}

		if (args.length > 1) {
			throw new IllegalArgumentException("Expecting 1 argument, but given " + args.length);
		}

		Quote quote = quoteGrabber.getQuote(args[0]);

		System.out.printf("%nQuote:%n\"%s\"\n- %s%n", quote.getQuote(), quote.getAuthor());
	}
}
