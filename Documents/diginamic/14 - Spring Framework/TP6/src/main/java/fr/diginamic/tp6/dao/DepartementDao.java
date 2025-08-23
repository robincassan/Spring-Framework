package fr.diginamic.tp6.dao;

import fr.diginamic.tp6.model.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO (Data Access Object) permettant la gestion des entités {@link Departement}
 * via l'API JPA (EntityManager).
 *
 * <p>Cette classe encapsule les opérations CRUD (Create, Read, Update, Delete)
 * sur les départements et sert d'interface entre la couche métier et la base
 * de données.</p>
 *
 * <p>Elle est annotée avec {@link Repository} pour indiquer à Spring qu'il
 * s'agit d'un composant persistant, et {@link Transactional} pour garantir
 * que chaque opération est exécutée dans une transaction.</p>
 */
@Repository
@Transactional
public class DepartementDao {

    /** Gestionnaire d'entités injecté par le conteneur Spring */
    @PersistenceContext
    private EntityManager em;

    /**
     * Récupère la liste de tous les départements en base.
     *
     * @return une {@link List} de {@link Departement}
     */
    public List<Departement> findAll() {
        return em.createQuery("SELECT d FROM Departement d", Departement.class).getResultList();
    }

    /**
     * Recherche un département par son identifiant.
     *
     * @param id identifiant unique du département
     * @return le {@link Departement} correspondant, ou {@code null} s'il n'existe pas
     */
    public Departement findById(Long id) {
        return em.find(Departement.class, id);
    }

    /**
     * Persiste un nouveau département en base.
     *
     * @param departement entité à enregistrer
     * @return l'entité persistée
     */
    public Departement save(Departement departement) {
        em.persist(departement);
        return departement;
    }

    /**
     * Met à jour un département existant.
     *
     * @param departement entité avec les nouvelles valeurs
     * @return l'entité fusionnée et mise à jour
     */
    public Departement update(Departement departement) {
        return em.merge(departement);
    }

    /**
     * Supprime un département de la base.
     *
     * @param departement entité à supprimer
     */
    public void delete(Departement departement) {
        em.remove(em.contains(departement) ? departement : em.merge(departement));
    }
}