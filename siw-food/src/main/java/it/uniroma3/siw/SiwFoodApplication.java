package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiwFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiwFoodApplication.class, args);
	}
	
	
	//TODO
	//1. rest
	//2. css
	//3. fare un link perenne all'index su tutte le pagine
	//4. reindirizzamento quando si clicca su risorsa proibita
	//5. sistemare il database definitivo e dimensioni immagini (aggiungere immagini a tutti gli ingredienti e i cuochi)
	//6. dare una schermata d'errore quando fallisce il login
	//7. username in alto a dx su tutte le pagine
	//8. annotazioni transactional
	//9. optional: deploy su cloud
	//10. optional: annotazioni internazionali per i messaggi di errore
	//11. optional: fare la roba antihacker su modifica ingredienti di una ricetta (del cuoco)
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

}
