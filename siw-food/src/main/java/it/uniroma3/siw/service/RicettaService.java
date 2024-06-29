package it.uniroma3.siw.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.RicettaRepository;

@Service
public class RicettaService {

	@Autowired
	private RicettaRepository ricettaRepository;

	public Iterable<Ricetta> findAll() {
		return this.ricettaRepository.findAll();
	}

	public Ricetta findById(Long id) {
		
		try {
			return this.ricettaRepository.findById(id).get();
		}
		
		catch(NoSuchElementException e) {
			return null;
		}
	}
	
	public Ricetta save(Ricetta ricetta) {
		return this.ricettaRepository.save(ricetta);
	}
	
	public boolean existsByTitoloAndCuoco(String titolo, Cuoco cuoco) {
		return this.ricettaRepository.existsByTitoloAndCuoco(titolo, cuoco);
	}
	
	public void delete(Ricetta ricetta) {
		Ricetta ricettaDaEliminare = this.ricettaRepository.findByTitoloAndCuoco(ricetta.getTitolo(), ricetta.getCuoco());
		this.ricettaRepository.delete(ricettaDaEliminare);
	}
	
	public Iterable<Ricetta> findAllByOrderByTitoloAsc() {
		return this.ricettaRepository.findAllByOrderByTitoloAsc();
	}

	public boolean existsByTitolo(String titolo) {
		return this.ricettaRepository.existsByTitolo(titolo);
	}
}
