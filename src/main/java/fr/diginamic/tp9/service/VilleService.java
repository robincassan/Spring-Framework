package fr.diginamic.tp9.service;

import fr.diginamic.tp9.exception.BusinessException;
import fr.diginamic.tp9.model.Ville;
import fr.diginamic.tp9.repository.VilleRepository;
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
    public Ville insertVille(Ville ville) throws BusinessException {
        validateVille(ville);
        return villeRepository.save(ville);
    }

    /**  Modifier une ville existante */
    public Ville modifierVille(Long idVille, Ville villeModifiee) throws BusinessException {
        Ville ville = villeRepository.findById(idVille).orElseThrow(() -> new BusinessException("Ville non trouvée"));
        validateVille(villeModifiee);
        ville.setNom(villeModifiee.getNom());
        ville.setPopulation(villeModifiee.getPopulation());
        ville.setCodePostal(villeModifiee.getCodePostal());
        ville.setDepartement(villeModifiee.getDepartement());
        return villeRepository.save(ville);
    }

    /**  Supprimer une ville par son ID */
    public void supprimerVille(Long idVille) {
        villeRepository.findById(idVille).ifPresent(villeRepository::delete);
    }

    /** --- Méthode de validation métier --- */
    private void validateVille(Ville ville) throws BusinessException {
        if (ville.getPopulation() < 10) {
            throw new BusinessException("La ville doit avoir au moins 10 habitants");
        }
        if (ville.getNom() == null || ville.getNom().length() < 2) {
            throw new BusinessException("Le nom de la ville doit contenir au moins 2 lettres");
        }
        if (ville.getDepartement() == null || ville.getDepartement().getCode() == null || ville.getDepartement().getCode().length() != 2) {
            throw new BusinessException("Le code département doit comporter 2 caractères");
        }
        if (villeRepository.existsByNomAndDepartementId(ville.getNom(), ville.getDepartement().getId())) {
            throw new BusinessException("Le nom de la ville doit être unique dans le département");
        }
    }

    // --- Étape 3 : Recherches avec gestion d’exception ---

    /** Recherche de toutes les villes dont le nom commence par un préfixe */
    public List<Ville> villesParNomPrefix(String prefix) {
        List<Ville> result = villeRepository.findByNomStartingWith(prefix);
        if (result.isEmpty()) {
            throw new BusinessException("Aucune ville dont le nom commence par " + prefix + " n’a été trouvée");
        }
        return result;
    }

    /** Recherche des villes dont la population est supérieure à min */
    public List<Ville> villesPopulationMin(int min) {
        List<Ville> result = villeRepository.findByPopulationGreaterThanOrderByPopulationDesc(min);
        if (result.isEmpty()) {
            throw new BusinessException("Aucune ville n’a une population supérieure à " + min);
        }
        return result;
    }

    /** Recherche des villes dont la population est entre min et max */
    public List<Ville> villesPopulationBetween(int min, int max) {
        List<Ville> result = villeRepository.findByPopulationBetweenOrderByPopulationDesc(min, max);
        if (result.isEmpty()) {
            throw new BusinessException("Aucune ville n’a une population comprise entre " + min + " et " + max);
        }
        return result;
    }

    /** Recherche des villes d’un département dont la population > min */
    public List<Ville> villesDepartementPopulationMin(Long departementId, int min, String codeDept) {
        List<Ville> result = villeRepository.findByDepartementIdAndPopulationGreaterThanOrderByPopulationDesc(departementId, min);
        if (result.isEmpty()) {
            throw new BusinessException("Aucune ville n’a une population supérieure à " + min + " dans le département " + codeDept);
        }
        return result;
    }

    /** Recherche des villes d’un département dont la population est entre min et max */
    public List<Ville> villesDepartementPopulationBetween(Long departementId, int min, int max, String codeDept) {
        List<Ville> result = villeRepository.findByDepartementIdAndPopulationBetweenOrderByPopulationDesc(departementId, min, max);
        if (result.isEmpty()) {
            throw new BusinessException("Aucune ville n’a une population comprise entre " + min + " et " + max + " dans le département " + codeDept);
        }
        return result;
    }

    /** Recherche des n villes les plus peuplées d’un département */
    public List<Ville> topNVillesDepartement(Long departementId, int n, String codeDept) {
        List<Ville> result = villeRepository.findByDepartementIdOrderByPopulationDesc(departementId, PageRequest.of(0, n));
        if (result.isEmpty()) {
            throw new BusinessException("Aucune ville trouvée dans le département " + codeDept);
        }
        return result;
    }

}
