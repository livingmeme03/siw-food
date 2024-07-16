package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User getUser(Long id) {
		return this.userRepository.findById(id).get();
	}
	
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}
	
	public void deleteCuocoAssociato(Long idUser) {
		this.userRepository.deleteCuocoAssociato(idUser);
	}
	
	public User findByCuoco(Cuoco cuoco) {
		
		
		return this.userRepository.findByCuoco(cuoco);
	}
	
	public void delete(User user) {
		this.userRepository.delete(user);
	}
	
}
