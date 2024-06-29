package it.uniroma3.siw.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;

public interface CuocoRepository extends CrudRepository<Cuoco, Long>{
	
	public boolean existsByNomeAndCognomeAndDataNascita(String nome, String cognome, LocalDate dataNascita);
	
	public boolean existsByNomeAndCognome(String nome, String cognome);
	
	public Cuoco findByNomeAndCognomeAndDataNascita(String nome, String cognome, LocalDate dataNascita);
	
	public Cuoco findByNomeAndCognome(String nome, String cognome);

	public Iterable<Cuoco> findAllByOrderByCognomeAsc();
}

