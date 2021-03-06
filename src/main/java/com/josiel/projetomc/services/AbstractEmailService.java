package com.josiel.projetomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.josiel.projetomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender; 
	
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
	
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context=new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido", context);
		
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		MimeMessage sm;
		try {
			sm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(sm);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			SendOrderConfirmationEmail(obj);
		}
		
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mm=javaMailSender.createMimeMessage();
		
			MimeMessageHelper mmh =new MimeMessageHelper(mm,true);
			mmh.setTo(obj.getCliente().getEmail());
			mmh.setFrom(sender);
			mmh.setSubject("Pedido Confirmado! Código: "+obj.getId());
			mmh.setText(htmlFromTemplatePedido(obj),true);
			 return mm;
			
		 
		
	}
}
