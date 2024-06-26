package it.uniroma3.siw.model;

import java.util.ArrayList;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	private String descrizione;
	@ElementCollection
	@CollectionTable(name = "ricette_ingredienti_quantità", joinColumns = @JoinColumn(name = "ricetta_id"))
	@MapKeyColumn(name = "nome")
	@Column(name = "quantità")
	@NotNull //probabilmente non serve perché non ho casistiche in cui questa mappa non viene creata
	private Map<Ingrediente, Integer> ingredienti;
	@NotNull
	@ManyToOne
	private Cuoco cuoco;
	private List<String> pathImmagini;
//	@ElementCollection
//	private List<Ingrediente> listaIngredienti;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
		return pathImmagini;
	}
	public void setPathImmagini(List<String> pathImmagini) {
		this.pathImmagini = pathImmagini;
	}
	public Set<Ingrediente> getListaIngredienti() {
		return this.ingredienti.keySet();		
	}
//	public void setListaIngredienti(List<Ingrediente> listaIngredienti) {
//		this.listaIngredienti = listaIngredienti;
//	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cuoco, ingredienti, nome);
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
		return Objects.equals(cuoco, other.cuoco) && Objects.equals(ingredienti, other.ingredienti)
				&& Objects.equals(nome, other.nome);
	}
	
	
	
	
}
