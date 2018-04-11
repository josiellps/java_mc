package com.josiel.projetomc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.josiel.projetomc.domain.Categoria;
import com.josiel.projetomc.domain.Cidade;
import com.josiel.projetomc.domain.Cliente;
import com.josiel.projetomc.domain.Endereco;
import com.josiel.projetomc.domain.Estado;
import com.josiel.projetomc.domain.Pagamento;
import com.josiel.projetomc.domain.PagamentoComBoleto;
import com.josiel.projetomc.domain.PagamentoComCartao;
import com.josiel.projetomc.domain.Pedido;
import com.josiel.projetomc.domain.Produto;
import com.josiel.projetomc.domain.enums.EstadoPagamento;
import com.josiel.projetomc.domain.enums.TipoCliente;
import com.josiel.projetomc.repositories.CategoriaRepository;
import com.josiel.projetomc.repositories.CidadeRepository;
import com.josiel.projetomc.repositories.ClienteRepository;
import com.josiel.projetomc.repositories.EnderecoRepository;
import com.josiel.projetomc.repositories.EstadoRepository;
import com.josiel.projetomc.repositories.PagamentoRepository;
import com.josiel.projetomc.repositories.PedidoRepository;
import com.josiel.projetomc.repositories.ProdutoRepository;

@SpringBootApplication
public class ProjetomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategoria().addAll(Arrays.asList(cat1));
		p2.getCategoria().addAll(Arrays.asList(cat1, cat2));
		p3.getCategoria().addAll(Arrays.asList(cat1));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "10756343690", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("982517350", "1146197388"));

		Endereco end1 = new Endereco(null, "Rua das couves", "301", "apto 303", "Jardim", "06604145", cli1, c1);
		Endereco end2 = new Endereco(null, "Rua das orquídeas", "30", "fundos", "capela", "04715002", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 17:45"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 17:45"), cli1, end2);

		Pagamento pgt1 = new PagamentoComCartao(null, EstadoPagamento.QUIATADO, ped1, 6);
		ped1.setPagamento(pgt1);

		Pagamento pgt2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 14:36"), null);
		ped2.setPagamento(pgt2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgt1,pgt2));
	}
}
