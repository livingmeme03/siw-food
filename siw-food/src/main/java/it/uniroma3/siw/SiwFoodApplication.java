package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiwFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiwFoodApplication.class, args);
	}
	
	
	//TODO
	//1. finire css
	//3. sistemare il database definitivo e dimensioni immagini (aggiungere immagini a tutti gli ingredienti e i cuochi)
	//4. username in alto a dx su tutte le pagine
	//5. annotazioni transactional
	//6. pulsante di logout 
	//9. optional: deploy su cloud
	//10. optional: annotazioni internazionali per i messaggi di errore
	// FATTO 8. sistemare messaggi di errore (not blank!)
	// FATTO 2. fare un link perenne all'index su tutte le pagine
	// MORTO 7. aggiungere errore se viene messa una lettera al posto della quantità dell'ingrediente
	// FATTO 1. rest
	// FATTO 4. ricerca ricetta per nome sbagliata mi porta al login??
	// FATTO 1. rimuovi ricette solo per nome (come aggiornamento dati? elenco con solo get?) -->attenzione mapping admin
	// MORTO 2. rimuovi ricette che non hanno cuoco (vedi codice, è un casino col parsing) -->attenzione mapping admin
	// FATTO 4. cuochi possono modificare/cancellare SOLO LE PROPRIE RICETTE
	// FATTO 3. differenziare i vari ruoli con quello che possono fare e aggiungere admin al database
	// FATTO 1. mettere unità di misura agli ingredienti (attributo e menu a tendina quando aggiungi un ingrediente)
	// FATTO 2. binding result sulle unità di misura di ingredienti di modifica ricetta
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
	// FATTO 5. dare una schermata d'errore quando fallisce il login
	// FATTO 3. reindirizzamento quando si clicca su risorsa proibita
	// FATTO 11. optional: fare la roba antihacker su modifica ingredienti di una ricetta (del cuoco)

}
