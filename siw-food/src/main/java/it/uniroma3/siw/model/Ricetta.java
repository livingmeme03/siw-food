package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ricetta {
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------ATTRIBUTI------------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String titolo;
	private String descrizione;
	@ElementCollection
	@CollectionTable(name = "ricette_ingredienti_quantità", joinColumns = @JoinColumn(name = "ricetta_id"))
	@MapKeyColumn(name = "nome")
	@Column(name = "quantità")			//cascadetype remove? per togliere gli ingredienti?
	private Map<Ingrediente, Integer> ingredienti;
	@ManyToOne
	private Cuoco cuoco;
	private List<String> pathImmagini;
	private String tuttiPathDelleImmagini;
//	@ElementCollection
//	private List<Ingrediente> listaIngredienti;
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------GETTER E SETTER----------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String nome) {
		this.titolo = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Map<Ingrediente, Integer> getIngredienti() {
		return ingredienti;
	}
	public void setIngredienti(Map<Ingrediente, Integer> ingredienti) {
		this.ingredienti = ingredienti;
	}
	public Cuoco getCuoco() {
		return cuoco;
	}
	public void setCuoco(Cuoco cuoco) {
		this.cuoco = cuoco;
	}
	public List<String> getPathImmagini() {
		return this.pathImmagini;
	}
	public void setPathImmagini(String path) {
		this.pathImmagini = Arrays.asList(path.split(","));
	}
	public String getTuttiPathDelleImmagini() {
		return tuttiPathDelleImmagini;
	}
	public void setTuttiPathDelleImmagini(String tuttiPathDelleImmagini) {
		this.tuttiPathDelleImmagini = tuttiPathDelleImmagini;
	}
	public Set<Ingrediente> getListaIngredienti() {
		return this.ingredienti.keySet();		
	}
//	public void setListaIngredienti(List<Ingrediente> listaIngredienti) {
//		this.listaIngredienti = listaIngredienti;
//	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------EQUALS E HASHCODE--------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	@Override
	public int hashCode() {
		return Objects.hash(titolo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ricetta other = (Ricetta) obj;
		return Objects.equals(titolo, other.titolo);
	}	
}
