package it.uniroma3.siw.service;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
	
	public boolean existsByNome(String nome) {
		return this.ingredienteRepository.existsByNome(nome);
	}
	
	public Ingrediente findByNome(String nome) {
		return this.ingredienteRepository.findByNome(nome);
	}
	
	public Iterable<Ingrediente> findAllByOrderByNomeAsc() {
		return this.ingredienteRepository.findAllByOrderByNomeAsc();
	}
	
	public Ingrediente save(Ingrediente ingrediente) {
		return this.ingredienteRepository.save(ingrediente);
	}
	
	
	public void delete(Ingrediente ingrediente) {
		Ingrediente ingredienteDaEliminare = this.ingredienteRepository.findByNome(ingrediente.getNome());
		this.ingredienteRepository.delete(ingredienteDaEliminare);
	}
	
	 
	public List<Ingrediente> findAllIngredientiInRicetta(Long ricettaid) {
		return this.ingredienteRepository.findAllIngredientiInRicetta(ricettaid);
	}
	
	public List<Ingrediente> findAllIngredientiInRicette() {
		return this.ingredienteRepository.findAllIngredientiInRicette();
	}
	
	public void saveIngredienteInRicetta(Long ingredienteid, Long ricettaid, Long quantità) {
		this.ingredienteRepository.saveIngredienteInRicetta(ingredienteid, ricettaid, quantità);
	}
	
	public void deleteIngredienteInRicetta(Long ingredienteid, Long ricettaid) {
		this.ingredienteRepository.deleteIngredienteInRicetta(ingredienteid, ricettaid);
	}
	
	public Long findIngredienteInRicette(Long ingredienteid) {
		return this.ingredienteRepository.findIngredienteInRicette(ingredienteid);
	}
	
	public void deleteIngredienteInAllRicette(Long ingredienteid) {
		this.ingredienteRepository.deleteIngredienteInAllRicette(ingredienteid);
	}
	
	

	
}
