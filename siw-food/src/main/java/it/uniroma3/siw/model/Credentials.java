package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Credentials {
	
	public static final String ADMIN_ROLE = "ADMIN";
	public static final String CUOCO_ROLE = "CUOCO";
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------ATTRIBUTI------------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	private String role;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------GETTER E SETTER----------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------EQUALS E HASHCODE--------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
//	@Override
//	public int hashCode() {
//		return Objects.hash(password, username);
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Credentials other = (Credentials) obj;
//		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
//	}
	
	
	
}
