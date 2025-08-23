package fr.diginamic.tp6.dao;

import fr.diginamic.tp6.model.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO (Data Access Object) permettant la gestion des entités {@link Ville}
 * via l'API JPA (EntityManager).
 *
 * <p>Cette classe encapsule les opérations CRUD (Create, Read, Update, Delete)
 * sur les villes et fournit une méthode supplémentaire pour rechercher une
 * ville par son nom.</p>
 *
 * <p>Elle est annotée avec {@link Repository} pour être reconnue comme un
 * composant persistant par Spring, et {@link Transactional} pour garantir
 * l'exécution de chaque opération dans une transaction.</p>
 */
@Repository
@Transactional
public class VilleDao {

    /** Gestionnaire d'entités injecté par le conteneur Spring */
    @PersistenceContext
    private EntityManager em;

    /**
     * Récupère la liste de toutes les villes en base.
     *
     * @return une {@link List} de {@link Ville}
     */
    public List<Ville> findAll() {
        return em.createQuery("SELECT v FROM Ville v", Ville.class).getResultList();
    }

    /**
     * Recherche une ville par son identifiant.
     *
     * @param id identifiant unique de la ville
     * @return la {@link Ville} correspondante, ou {@code null} si elle n'existe pas
     */
    public Ville findById(Long id) {
        return em.find(Ville.class, id);
    }

    /**
     * Recherche une ville par son nom exact.
     *
     * @param nom nom de la ville
     * @return la {@link Ville} correspondante
     * @throws jakarta.persistence.NoResultException si aucune ville n'est trouvée
     */
    public Ville findByNom(String nom) {
        return em.createQuery("SELECT v FROM Ville v WHERE v.nom = :nom", Ville.class)
                .setParameter("nom", nom)
                .getSingleResult();
    }

    /**
     * Persiste une nouvelle ville en base.
     *
     * @param ville entité à enregistrer
     * @return l'entité persistée
     */
    public Ville save(Ville ville) {
        em.persist(ville);
        return ville;
    }

    /**
     * Met à jour une ville existante.
     *
     * @param ville entité avec les nouvelles valeurs
     * @return l'entité fusionnée et mise à jour
     */
    public Ville update(Ville ville) {
        return em.merge(ville);
    }

    /**
     * Supprime une ville de la base.
     *
     * @param ville entité à supprimer
     */
    public void delete(Ville ville) {
        em.remove(em.contains(ville) ? ville : em.merge(ville));
    }
}
