package fr.diginamic.tp6.service;

import fr.diginamic.tp6.model.Ville;
import fr.diginamic.tp6.repository.VilleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

    private final VilleRepository villeRepository;

    public VilleService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    /**  Récupérer toutes les villes avec pagination */
    public Page<Ville> extractVilles(int page, int size) {
        return villeRepository.findAll(PageRequest.of(page, size));
    }

    /**  Récupérer une ville par son ID */
    public Ville extractVille(Long idVille) {
        return villeRepository.findById(idVille).orElse(null);
    }

    /**  Récupérer une ville par son nom */
    public Ville extractVille(String nom) {
        return villeRepository.findByNom(nom).orElse(null);
    }

    /**  Ajouter une nouvelle ville */
    public Ville insertVille(Ville ville) {
        return villeRepository.save(ville);
    }

    /**  Modifier une ville existante */
    public Ville modifierVille(Long idVille, Ville villeModifiee) {
        Ville ville = villeRepository.findById(idVille).orElse(null);
        if (ville != null) {
            ville.setNom(villeModifiee.getNom());
            ville.setPopulation(villeModifiee.getPopulation());
            ville.setCodePostal(villeModifiee.getCodePostal());
            ville.setDepartement(villeModifiee.getDepartement());
            return villeRepository.save(ville); // save retourne l'entité persistée
        }
        return null;
    }

    /**  Supprimer une ville par son ID */
    public void supprimerVille(Long idVille) {
        villeRepository.findById(idVille).ifPresent(villeRepository::delete);
    }

    /**  Rechercher les villes dont le nom commence par un préfixe */
    public List<Ville> villesParNomPrefix(String prefix) {
        return villeRepository.findAll().stream()
                .filter(v -> v.getNom().startsWith(prefix))
                .toList();
    }

    /**  Rechercher les villes dont la population est supérieure à min */
    public List<Ville> villesPopulationMin(int min) {
        return villeRepository.findAll().stream()
                .filter(v -> v.getPopulation() > min)
                .sorted((v1, v2) -> Integer.compare(v2.getPopulation(), v1.getPopulation()))
                .toList();
    }

    /**  Rechercher les villes dont la population est entre min et max */
    public List<Ville> villesPopulationBetween(int min, int max) {
        return villeRepository.findAll().stream()
                .filter(v -> v.getPopulation() > min && v.getPopulation() < max)
                .sorted((v1, v2) -> Integer.compare(v2.getPopulation(), v1.getPopulation()))
                .toList();
    }

    /**  Rechercher les n villes les plus peuplées d’un département */
    public List<Ville> topNVillesDepartement(Long departementId, int n) {
        return villeRepository.findByDepartementIdOrderByPopulationDesc(departementId, PageRequest.of(0, n));
    }
}
