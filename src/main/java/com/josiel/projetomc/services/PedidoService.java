package com.josiel.projetomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josiel.projetomc.domain.ItemPedido;
import com.josiel.projetomc.domain.PagamentoComBoleto;
import com.josiel.projetomc.domain.Pedido;
import com.josiel.projetomc.domain.Produto;
import com.josiel.projetomc.domain.enums.EstadoPagamento;
import com.josiel.projetomc.repositories.ItemPedidoRepository;
import com.josiel.projetomc.repositories.PagamentoRepository;
import com.josiel.projetomc.repositories.PedidoRepository;
import com.josiel.projetomc.repositories.ProdutoRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido buscar(Integer id) {
		Optional<Pedido> cat = repo.findById(id);
		if (cat == null) {
			try {
				throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id);
			} catch (ObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cat.orElse(null);
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setPagamento(EstadoPagamento.PENDENTE);
		;
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		pagamentoRepository.save(obj.getPagamento());

		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			Optional<Produto> p = produtoRepository.findById(ip.getProduto().getId());
			ip.setPreco(p.orElse(null).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;

	}
}
