package fr.diginamic.tp9.repository;

import fr.diginamic.tp9.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Spring Data JPA pour l'entité {@link Departement}.
 *
 * <p>Cette interface hérite de {@link JpaRepository} et fournit ainsi
 * automatiquement toutes les opérations CRUD (Create, Read, Update, Delete)
 * ainsi que des méthodes de pagination et de tri.</p>
 *
 * <p>Exemples de méthodes héritées :</p>
 * <ul>
 *     <li>{@code save(Departement entity)} : enregistre ou met à jour un département</li>
 *     <li>{@code findById(Long id)} : recherche un département par son identifiant</li>
 *     <li>{@code findAll()} : récupère tous les départements</li>
 *     <li>{@code deleteById(Long id)} : supprime un département par son identifiant</li>
 * </ul>
 *
 * <p>Il est possible d'ajouter ici des méthodes de requête personnalisées
 * en suivant la convention de nommage de Spring Data JPA
 * (par exemple {@code findByNom(String nom)}).</p>
 */
public interface DepartementRepository extends JpaRepository<Departement, Long> {

    // pour vérifier l’unicité du nom
    boolean existsByNom(String nom);

}

