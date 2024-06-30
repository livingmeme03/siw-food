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
	//2. aggiungere ricette ai cuochi	(addactorstomovie)
	//3. mettere unità di misura agli ingredienti (attributo e menu a tendina quando aggiungi un ingrediente)
	//4. ricerca ricette/cuochi/ingredienti per nome/cuoco///nome/datanascita///nome		(searchmovies)
	//5. rimuovi ingredienti da ricetta (credo sia insieme a quella per aggiungere ingredienti alla ricetta)
	//6. rimuovi ingredienti in generale???
	//7. rimuovi ricette solo per nome (come aggiornamento dati? elenco con solo get?)
	//8. rimuovi ricette che non hanno cuoco (vedi codice, è un casino col parsing)
	//9. differenziare i vari ruoli con quello che possono fare e aggiungere admin al database
	//10. cuochi possono modificare/cancellare SOLO LE PROPRIE RICETTE
	//11. sistemare il database definitivo e dimensioni immagini (aggiungere immagini a tutti gli ingredienti e i cuochi)
	//12. css
	//13. rest
	//14. annotazioni transactional
	//15. optional: deploy su cloud
	//16. optional: annotazioni internazionali per i messaggi di errore
	// FATTO 2. aggiungere cuoco alla ricetta	(setdirectortomovie)
	// FATTO 4. aggiungere ricette solo attributi semplici		(aggiungifilm)
	// FATTO (da sistemare ingrediente) 4. rimuovi ricette/cuochi/ingredienti
	// FATTO 6. controlli da fare su authentication col binding result
	// FATTO 7. controllo duplicati sul login

}
