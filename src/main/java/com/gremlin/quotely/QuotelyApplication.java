package com.gremlin.quotely;

import com.gremlin.quotely.quotes.Quote;
import com.gremlin.quotely.quotes.QuoteGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuotelyApplication implements CommandLineRunner {
	@Autowired private QuoteGrabber quoteGrabber;

	public static void main(String[] args) {
		SpringApplication.run(QuotelyApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Quote quote = quoteGrabber.getQuote(args[0]);

		System.out.printf("%n\"%s\"\n- %s%n", quote.getQuote(), quote.getAuthor());
	}
}
