package com.josiel.projetomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.josiel.projetomc.domain.Pedido;

public interface EmailService {

	void SendOrderConfirmationEmail(Pedido objPedido);
	
	void SendEmail(SimpleMailMessage msg);	
}
