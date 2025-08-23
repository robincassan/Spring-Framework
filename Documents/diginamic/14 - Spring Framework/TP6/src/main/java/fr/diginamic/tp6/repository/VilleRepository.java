package fr.diginamic.tp6.repository;

import fr.diginamic.tp6.model.Ville;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Spring Data JPA pour l'entité {@link Ville}.
 *
 * <p>Cette interface hérite de {@link JpaRepository} et fournit
 * toutes les opérations CRUD (Create, Read, Update, Delete) ainsi que
 * des fonctionnalités de pagination et de tri.</p>
 *
 * <p>En plus des méthodes héritées, elle définit plusieurs
 * requêtes personnalisées grâce à la convention de nommage Spring Data JPA :</p>
 *
 * <ul>
 *   <li>{@link #findByNom(String)} : recherche une ville par son nom</li>
 *   <li>{@link #findByDepartementId(Long)} : récupère toutes les villes d'un département donné</li>
 *   <li>{@link #findByDepartementIdAndPopulationBetween(Long, int, int)} :
 *       récupère les villes d'un département dont la population est comprise entre deux bornes</li>
 * </ul>
 *
 * <p>Ces méthodes sont automatiquement implémentées par Spring Data JPA
 * en fonction de leur nom.</p>
 */
public interface VilleRepository extends JpaRepository<Ville, Long> {

    /**
     * Recherche une ville par son nom.
     *
     * @param nom le nom de la ville
     * @return un {@link Optional} contenant la ville si trouvée, sinon vide
     */
    Optional<Ville> findByNom(String nom); // pour recherche par nom

    // Trouver toutes les villes d’un département
    /**
     * Récupère toutes les villes appartenant à un département spécifique.
     *
     * @param departementId l'identifiant du département
     * @return la liste des villes de ce département
     */
    List<Ville> findByDepartementId(Long departementId);

    // Trouver toutes les villes d’un département avec population entre min et max
    /**
     * Récupère toutes les villes d’un département dont la population
     * est comprise entre deux bornes.
     *
     * @param departementId l'identifiant du département
     * @param min borne minimale de population
     * @param max borne maximale de population
     * @return la liste des villes correspondant aux critères
     */
    List<Ville> findByDepartementIdAndPopulationBetween(Long departementId, int min, int max);
}
