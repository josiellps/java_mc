package com.josiel.projetomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.josiel.projetomc.services.DBService;
import com.josiel.projetomc.services.EmailService;
import com.josiel.projetomc.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {

		dbService.instantiateDatabase();
		return true;
	}

	@Bean
	public SmtpEmailService emailSerivce() {
		return new SmtpEmailService();
	}
}