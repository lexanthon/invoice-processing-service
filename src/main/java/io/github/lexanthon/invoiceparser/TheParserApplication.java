package io.github.lexanthon.invoiceparser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.lexanthon.invoiceparser.utils.ParserUtils;

@SpringBootApplication
@EnableScheduling
public class TheParserApplication extends SpringBootServletInitializer   {

	
	@Autowired
	private ParserUtils parserUtils;
	
	public static void main(String[] args) {
		SpringApplication.run(TheParserApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		parserUtils.checkInvoicesByStatus();     
//	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TheParserApplication.class);
	}

}

