package com.josiel.projetomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.josiel.projetomc.domain.Cidade;
import com.josiel.projetomc.domain.Cliente;
import com.josiel.projetomc.domain.Endereco;
import com.josiel.projetomc.domain.enums.TipoCliente;
import com.josiel.projetomc.dto.ClienteDTO;
import com.josiel.projetomc.dto.ClienteNewDTO;
import com.josiel.projetomc.repositories.CidadeRepository;
import com.josiel.projetomc.repositories.ClienteRepository;
import com.josiel.projetomc.repositories.EnderecoRepository;
import com.josiel.projetomc.services.exceptions.DataIntegrityException;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> cat = repo.findById(id);
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

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas.");
		}
	}

	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		@SuppressWarnings("deprecation")
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO obj) {
		Cliente cliente = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCpfOuCnpj(),
				TipoCliente.toEnum(obj.getTipo()));
		Optional<Cidade> cid = cidadeRepository.findById(obj.getCidadeId());
		Endereco endereco = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(),
				obj.getBairro(), obj.getCep(), cliente, cid.orElse(null));
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(obj.getTelefone1());
		if (obj.getTelefone2() != null) {
			cliente.getTelefones().add(obj.getTelefone2());
		}
		if (obj.getTelefone3() != null) {
			cliente.getTelefones().add(obj.getTelefone3());
		}
		return cliente;
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		Cliente objTemp = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return objTemp;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
