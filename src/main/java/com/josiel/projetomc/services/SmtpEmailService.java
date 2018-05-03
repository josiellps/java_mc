package com.josiel.projetomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {
	@Autowired
	private MailSender mailSender;

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

	@Override
	public void SendEmail(SimpleMailMessage msg) {
		// TODO Auto-generated method stub
		LOG.info("Enviando envio de email");
		mailSender.send(msg);
		LOG.info("Email enviado");
	}

}