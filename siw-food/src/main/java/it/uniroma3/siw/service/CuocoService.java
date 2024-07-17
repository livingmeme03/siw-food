package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.CuocoRepository;
import jakarta.validation.Valid;

@Service
public class CuocoService {
	
	@Autowired
	private CuocoRepository cuocoRepository;
	
	public Iterable<Cuoco> findAll() {
		return this.cuocoRepository.findAll();
	}

	public Cuoco findById(Long id) {
		
		try {
			return this.cuocoRepository.findById(id).get();
		}
		catch(NoSuchElementException e) {
			return null;
		}
	}
	
	public Cuoco save(Cuoco cuoco) {
		return this.cuocoRepository.save(cuoco);
	}
	
	public boolean existsByNomeAndCognomeAndDataNascita(String nome, String cognome, LocalDate dataNascita) {
		return this.cuocoRepository.existsByNomeAndCognomeAndDataNascita(nome, cognome, dataNascita);
	}
	
	public Cuoco findByNomeAndCognomeAndDataNascita(String nome, String cognome, LocalDate dataNascita) {
		return this.cuocoRepository.findByNomeAndCognomeAndDataNascita(nome, cognome, dataNascita);
	}
	
	public Cuoco findByNomeAndCognome(String nome, String cognome) {
		return this.cuocoRepository.findByNomeAndCognome(nome, cognome);
	}

	public void delete(Cuoco cuoco) {
		Cuoco cuocoDaEliminare = this.cuocoRepository.findByNomeAndCognomeAndDataNascita(cuoco.getNome(), cuoco.getCognome(), cuoco.getDataNascita());
		this.cuocoRepository.delete(cuocoDaEliminare);
	}

	public Iterable<Cuoco> findAllByOrderByCognomeAsc() {
		return this.cuocoRepository.findAllByOrderByCognomeAsc();
	}

	public Iterable<Cuoco> findByCognome(String cognome) {
		return this.cuocoRepository.findByCognome(cognome);
	}

	public Long count() {
		return this.cuocoRepository.count();
	}
}

