package fr.diginamic.tp6.service;

import fr.diginamic.tp6.dao.VilleDao;
import fr.diginamic.tp6.model.Ville;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

    private final VilleDao villeDao;

    public VilleService(VilleDao villeDao) {
        this.villeDao = villeDao;
    }

    public List<Ville> extractVilles() {
        return villeDao.findAll();
    }

    public Ville extractVille(Long idVille) {
        return villeDao.findById(idVille);
    }

    public Ville extractVille(String nom) {
        return villeDao.findByNom(nom);
    }

    public List<Ville> insertVille(Ville ville) {
        villeDao.save(ville);
        return extractVilles();
    }

    public List<Ville> modifierVille(Long idVille, Ville villeModifiee) {
        Ville ville = villeDao.findById(idVille);
        if (ville != null) {
            ville.setNom(villeModifiee.getNom());
            ville.setPopulation(villeModifiee.getPopulation());
            ville.setCodePostal(villeModifiee.getCodePostal());
            villeDao.update(ville);
        }
        return extractVilles();
    }

    public List<Ville> supprimerVille(Long idVille) {
        Ville ville = villeDao.findById(idVille);
        if (ville != null) {
            villeDao.delete(ville);
        }
        return extractVilles();
    }
}
