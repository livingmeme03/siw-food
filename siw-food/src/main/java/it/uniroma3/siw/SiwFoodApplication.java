package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiwFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiwFoodApplication.class, args);
	}
	
	//TODO
	//1. aggiungere ingredienti alle ricette (nuova form)
	//2. ricerca ricette/cuochi/ingredienti per nome/cuoco///nome/datanascita///nome
	//3. modifica ricette/cuochi/ingredienti
	//4. rimuovi ricette/cuochi/ingredienti
	//5. differenziare i vari ruoli con quello che possono fare e aggiungere admin al database
	//6. controlli da fare su authentication col binding result
	//7. controllo duplicati sul login
	//8. sistemare il database definitivo e dimensioni immagini
	//9. css
	//10. rest
	//11. annotazioni transactional
	//12. optional: deploy su cloud
	//13. optional: annotazioni internazionali per i messaggi di errore

}
