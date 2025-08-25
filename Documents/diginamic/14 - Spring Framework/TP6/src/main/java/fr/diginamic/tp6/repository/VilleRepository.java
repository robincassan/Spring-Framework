package fr.diginamic.tp6.repository;

import fr.diginamic.tp6.model.Ville;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Spring Data JPA pour l'entité {@link Ville}.
 *
 * <p>Cette interface hérite de {@link JpaRepository} et fournit
 * toutes les opérations CRUD ainsi que des fonctionnalités de pagination et de tri.</p>
 *
 * <p>Elle contient aussi des méthodes de recherche personnalisées</p>
 */
public interface VilleRepository extends JpaRepository<Ville, Long> {

    // --- Recherche simple ---
    Optional<Ville> findByNom(String nom);

    // --- Recherche par département ---
    List<Ville> findByDepartementId(Long departementId);

    // --- Population entre min et max dans un département ---
    List<Ville> findByDepartementIdAndPopulationBetween(Long departementId, int min, int max);

    // --- Nouvelles méthodes pour l'étape 3 ---

    // Villes dont le nom commence par une chaîne
    List<Ville> findByNomStartingWith(String prefix);

    // Villes avec population supérieure à min (toutes)
    List<Ville> findByPopulationGreaterThanOrderByPopulationDesc(int min);

    // Villes avec population entre min et max (toutes)
    List<Ville> findByPopulationBetweenOrderByPopulationDesc(int min, int max);

    // Villes d’un département avec population supérieure à min
    List<Ville> findByDepartementIdAndPopulationGreaterThanOrderByPopulationDesc(Long departementId, int min);

    // Villes d’un département avec population entre min et max
    List<Ville> findByDepartementIdAndPopulationBetweenOrderByPopulationDesc(Long departementId, int min, int max);

    // Les n villes les plus peuplées d’un département (via Pageable)
    List<Ville> findByDepartementIdOrderByPopulationDesc(Long departementId, Pageable pageable);
}
