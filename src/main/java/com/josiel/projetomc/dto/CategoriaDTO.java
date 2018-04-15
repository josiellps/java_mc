package com.josiel.projetomc.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.josiel.projetomc.domain.Categoria;

public class CategoriaDTO {

	private Integer id;
	@NotEmpty(message="Preenchimento obrigatório.")
	@Length(min=5,max=80,message="Preenchimento deve ser entre 5 e 80 caracteres.")
	private String nome;

	public CategoriaDTO(Categoria obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public CategoriaDTO() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
