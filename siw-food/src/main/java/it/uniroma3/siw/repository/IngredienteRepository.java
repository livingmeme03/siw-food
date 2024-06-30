package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Ingrediente;
import jakarta.transaction.Transactional;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long>{

	public boolean existsByNome(String nome);

	public Ingrediente findByNome(String nome);

	public Iterable<Ingrediente> findAllByOrderByNomeAsc();
	
	@Query(value = "select ingredienti_key from ricette_ingredienti_quantità where ricetta_id = :ricetta_id", nativeQuery = true)
	public List<Ingrediente> findAllIngredientiInRicetta(@Param("ricetta_id") Long ricetta_id);
	
	@Transactional
	@Modifying
	@Query(value = "insert into ricette_ingredienti_quantità (quantità, ingredienti_key, ricetta_id) values (2 , :ingredienti_key, :ricetta_id)", nativeQuery = true)
	public void saveIngredienteInRicetta(@Param("ingredienti_key") Long ingredienti_key, @Param("ricetta_id") Long ricetta_id);
	
	@Transactional
	@Modifying
	@Query(value = "delete from ricette_ingredienti_quantità where ricetta_id = :ricetta_id and ingredienti_key = :ingredienti_key", nativeQuery = true)
	public void deleteIngredienteInRicetta(@Param("ingredienti_key") Long ingredienti_key, @Param("ricetta_id") Long ricetta_id);
}
	
	
