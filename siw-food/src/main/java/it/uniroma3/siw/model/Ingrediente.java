package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ingrediente {
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------ATTRIBUTI------------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	
	private String pathImmagine;
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------GETTER E SETTER----------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
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
	public String getPathImmagine() {
		return pathImmagine;
	}
	public void setPathImmagine(String pathImmagine) {
		this.pathImmagine = pathImmagine;
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------EQUALS E HASHCODE--------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		return Objects.equals(nome, other.nome);
	}
	
	
}
