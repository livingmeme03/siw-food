package it.uniroma3.siw.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.repository.IngredienteRepository;
import it.uniroma3.siw.model.Ingrediente;

@Service
public class IngredienteService {
	
	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	public Iterable<Ingrediente> findAll() {
		return this.ingredienteRepository.findAll();
	}

	public Ingrediente findById(Long id) {
		return this.ingredienteRepository.findById(id).get();
	}
}
