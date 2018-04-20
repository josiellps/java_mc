package com.josiel.projetomc.services;

import java.util.Calendar;
import java.util.Date;

import com.josiel.projetomc.domain.PagamentoComBoleto;

public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pgto, Date instanteDoPedido) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pgto.setDataVencimento(cal.getTime());
	}
}
