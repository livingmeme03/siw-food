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
	//2. binding result sulle unità di misura di ingredienti di modifica ricetta
	//3. rimuovi ricette solo per nome (come aggiornamento dati? elenco con solo get?)
	//4. rimuovi ricette che non hanno cuoco (vedi codice, è un casino col parsing)
	//5. differenziare i vari ruoli con quello che possono fare e aggiungere admin al database
	//6. cuochi possono modificare/cancellare SOLO LE PROPRIE RICETTE
	//7. sistemare il database definitivo e dimensioni immagini (aggiungere immagini a tutti gli ingredienti e i cuochi)
	//8. css
	//9. rest
	//10. annotazioni transactional
	//11. optional: deploy su cloud
	//12. optional: annotazioni internazionali per i messaggi di errore
	// FATTO 2. ricerca ingredienti per nome		(searchmovies)
	// FATTO ricerca ricette e cuochi
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
