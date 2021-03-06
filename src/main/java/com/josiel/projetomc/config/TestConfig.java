package com.josiel.projetomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.josiel.projetomc.services.DBService;
import com.josiel.projetomc.services.EmailService;
import com.josiel.projetomc.services.MockMailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateDatabase();
		return true;
	}

	@Bean
	public EmailService emailSerivce() {
		return new MockMailService();
	}
}
