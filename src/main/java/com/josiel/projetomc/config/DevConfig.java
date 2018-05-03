package com.josiel.projetomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.josiel.projetomc.services.DBService;
import com.josiel.projetomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if (!"create".equals(strategy)) {			
			return false;
		}
		dbService.instantiateDatabase();
		return true;
	}

	@Bean
	public SmtpEmailService emailSerivce() {
		return new SmtpEmailService();
	}
}
