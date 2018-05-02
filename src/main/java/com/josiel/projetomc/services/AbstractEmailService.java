package com.josiel.projetomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.josiel.projetomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Override
	public void SendOrderConfirmationEmail(Pedido objPedido){
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(objPedido);
		SendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido objPedido) {
		// TODO Auto-generated method stub
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(objPedido.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado: " + objPedido.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(objPedido.toString());
		return sm;
	}
}
