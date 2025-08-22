package fr.diginamic.tp6.dao;

import fr.diginamic.tp6.model.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class VilleDao {

    @PersistenceContext
    private EntityManager em;

    public List<Ville> findAll() {
        return em.createQuery("SELECT v FROM Ville v", Ville.class).getResultList();
    }

    public Ville findById(Long id) {
        return em.find(Ville.class, id);
    }

    public Ville findByNom(String nom) {
        return em.createQuery("SELECT v FROM Ville v WHERE v.nom = :nom", Ville.class)
                .setParameter("nom", nom)
                .getSingleResult();
    }

    public Ville save(Ville ville) {
        em.persist(ville);
        return ville;
    }

    public Ville update(Ville ville) {
        return em.merge(ville);
    }

    public void delete(Ville ville) {
        em.remove(em.contains(ville) ? ville : em.merge(ville));
    }
}
