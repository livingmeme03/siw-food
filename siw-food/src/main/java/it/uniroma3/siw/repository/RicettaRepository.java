package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;

public interface RicettaRepository extends CrudRepository<Ricetta, Long>{
	
	public boolean existsByTitoloAndCuoco(String titolo, Cuoco cuoco);

	public Ricetta findByTitoloAndCuoco(String titolo, Cuoco cuoco);

	public Iterable<Ricetta> findAllByOrderByTitoloAsc();

}
