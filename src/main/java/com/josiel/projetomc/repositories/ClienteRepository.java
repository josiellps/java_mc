package com.josiel.projetomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.josiel.projetomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer>{


}
