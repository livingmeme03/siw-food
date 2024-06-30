package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiwFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiwFoodApplication.class, args);
	}
	
	//TODO
	//1. mettere unità di misura agli ingredienti (attributo e menu a tendina quando aggiungi un ingrediente)
	//2. ricerca ricette/cuochi/ingredienti per nome/cuoco///nome/datanascita///nome		(searchmovies)
	//4. rimuovi ricette solo per nome (come aggiornamento dati? elenco con solo get?)
	//5. rimuovi ricette che non hanno cuoco (vedi codice, è un casino col parsing)
	//6. differenziare i vari ruoli con quello che possono fare e aggiungere admin al database
	//7. cuochi possono modificare/cancellare SOLO LE PROPRIE RICETTE
	//8. sistemare il database definitivo e dimensioni immagini (aggiungere immagini a tutti gli ingredienti e i cuochi)
	//9. css
	//10. rest
	//11. annotazioni transactional
	//12. optional: deploy su cloud
	//13. optional: annotazioni internazionali per i messaggi di errore
	// FATTO 3. rimuovi ingredienti in generale
	// FATTO 2. aggiungere ricette ai cuochi	(addactorstomovie)
	// FATTO 2. aggiungere cuoco alla ricetta	(setdirectortomovie)
	// FATTO 4. aggiungere ricette solo attributi semplici		(aggiungifilm)
	// FATTO (da sistemare ingrediente) 4. rimuovi ricette/cuochi/ingredienti
	// FATTO 6. controlli da fare su authentication col binding result
	// FATTO 7. controllo duplicati sul login
	// FATTO 5. rimuovi ingredienti da ricetta (credo sia insieme a quella per aggiungere ingredienti alla ricetta)
	// FATTO 1. aggiungere ingredienti alla ricetta (nuova form) (menu a tendina con tutti gli ingredienti?) (addactorstomovie)

}
