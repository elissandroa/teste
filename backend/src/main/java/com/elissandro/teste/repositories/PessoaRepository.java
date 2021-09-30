package com.elissandro.teste.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elissandro.teste.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
