package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiwFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiwFoodApplication.class, args);
	}
	
	//TODO
	//1. aggiungere ingredienti alle ricette (nuova form) (menu a tendina con tutti gli ingredienti?)
	//2. aggiungere unità di misura agli ingredienti (attributo e menu a tendina quando aggiungi un ingrediente)
	//2. ricerca ricette/cuochi/ingredienti per nome/cuoco///nome/datanascita///nome
	//3. modifica ricette/cuochi/ingredienti
	// FATTO (da sistemare ingrediente) 4. rimuovi ricette/cuochi/ingredienti
	//4.5 rimuovi ingredienti da ricetta
	//5. differenziare i vari ruoli con quello che possono fare e aggiungere admin al database
	// FATTO 6. controlli da fare su authentication col binding result
	// FATTO 7. controllo duplicati sul login
	//8. sistemare il database definitivo e dimensioni immagini (aggiungere immagini a tutti gli ingredienti e i cuochi)
	//9. css
	//10. rest
	//11. annotazioni transactional
	//12. optional: deploy su cloud
	//13. optional: annotazioni internazionali per i messaggi di errore

}
