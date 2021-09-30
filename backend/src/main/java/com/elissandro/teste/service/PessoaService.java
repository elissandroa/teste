package com.elissandro.teste.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elissandro.teste.dto.PessoaDTO;
import com.elissandro.teste.entities.Pessoa;
import com.elissandro.teste.repositories.PessoaRepository;
import com.elissandro.teste.service.exceptions.ResourceNotFoundException;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	
	@Transactional(readOnly = true)
	public List<PessoaDTO> findALL(){
		List<Pessoa> list = repository.findAll();
		return list.stream().map(x -> new PessoaDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public PessoaDTO findById(Long id) {
		Optional<Pessoa> obj = repository.findById(id);
		Pessoa entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
		return new PessoaDTO(entity);
	}

}
