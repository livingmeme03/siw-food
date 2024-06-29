package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiwFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiwFoodApplication.class, args);
	}
	
	//TODO
	//1. aggiungere ingredienti alla ricetta (nuova form) (menu a tendina con tutti gli ingredienti?) (addactorstomovie)
	//2. aggiungere cuoco alla ricetta	(setdirectortomovie)
	//3. aggiungere ricette ai cuochi	(addactorstomovie)
	//4. aggiungere ricette solo attributi semplici		(aggiungifilm)
	//5. mettere unità di misura agli ingredienti (attributo e menu a tendina quando aggiungi un ingrediente)
	//6. ricerca ricette/cuochi/ingredienti per nome/cuoco///nome/datanascita///nome		(searchmovies)
	//7. rimuovi ingredienti da ricetta (credo sia insieme a quella per aggiungere ingredienti alla ricetta)
	// FATTO (da sistemare ingrediente) 4. rimuovi ricette/cuochi/ingredienti
	//8. differenziare i vari ruoli con quello che possono fare e aggiungere admin al database
	// FATTO 6. controlli da fare su authentication col binding result
	// FATTO 7. controllo duplicati sul login
	//9. sistemare il database definitivo e dimensioni immagini (aggiungere immagini a tutti gli ingredienti e i cuochi)
	//10. css
	//11. rest
	//12. annotazioni transactional
	//13. optional: deploy su cloud
	//14. optional: annotazioni internazionali per i messaggi di errore

}
