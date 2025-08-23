package fr.diginamic.tp6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application TP6.
 *
 * <p>Cette classe est annotée avec {@link SpringBootApplication}, ce qui
 * active la configuration automatique de Spring Boot et la détection des
 * composants dans le package {@code fr.diginamic.tp6} et ses sous-packages.</p>
 *
 * <p>Elle contient la méthode {@link #main(String[]) main} qui lance
 * l'application Spring Boot.</p>
 */
@SpringBootApplication
public class Tp6Application {

	/**
	 * Point d'entrée de l'application Spring Boot.
	 *
	 * @param args arguments passés depuis la ligne de commande (non utilisés)
	 */
	public static void main(String[] args) {
		SpringApplication.run(Tp6Application.class, args);
	}

}
