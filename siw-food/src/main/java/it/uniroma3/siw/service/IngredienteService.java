package it.uniroma3.siw.service;


import java.util.NoSuchElementException;

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

		try {
			return this.ingredienteRepository.findById(id).get();
		}

		catch(NoSuchElementException e) {
			return null;
		}
	}
	
	public Ingrediente save(Ingrediente ingrediente) {
		return this.ingredienteRepository.save(ingrediente);
	}
	
	public boolean existsByNome(String nome) {
		return this.ingredienteRepository.existsByNome(nome);
	}
	
	public void delete(Ingrediente ingrediente) {
		Ingrediente ingredienteDaEliminare = this.ingredienteRepository.findByNome(ingrediente.getNome());
		this.ingredienteRepository.delete(ingredienteDaEliminare);
	}

	public Iterable<Ingrediente> findAllByOrderByNomeAsc() {
		return this.ingredienteRepository.findAllByOrderByNomeAsc();
	}
}
