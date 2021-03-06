package com.elissandro.teste.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elissandro.teste.dto.PessoaDTO;
import com.elissandro.teste.entities.Pessoa;
import com.elissandro.teste.repositories.PessoaRepository;
import com.elissandro.teste.service.exceptions.DatabaseException;
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
	
	@Transactional
	public PessoaDTO insert(PessoaDTO dto) {
		Pessoa entity = new Pessoa();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new PessoaDTO(entity);
	}
	
	@Transactional
	public PessoaDTO update(Long id, PessoaDTO dto) {
		try {
			Pessoa entity = repository.getById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new PessoaDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	

	private void copyDtoToEntity(PessoaDTO dto, Pessoa entity) {
		entity.setNome(dto.getNome());
		entity.setEmail(dto.getEmail());
		entity.setCpf(dto.getCpf());
	}
}
