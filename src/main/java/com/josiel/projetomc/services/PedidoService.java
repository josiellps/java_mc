package com.josiel.projetomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josiel.projetomc.domain.Pedido;
import com.josiel.projetomc.repositories.PedidoRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

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
}
