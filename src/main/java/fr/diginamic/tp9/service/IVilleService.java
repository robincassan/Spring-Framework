package fr.diginamic.tp9.service;

import fr.diginamic.tp9.exception.BusinessException;
import fr.diginamic.tp9.model.Ville;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IVilleService {
    Page<Ville> extractVilles(int page, int size);

    Ville extractVille(Long idVille);

    Ville extractVille(String nom);

    Ville insertVille(Ville ville) throws BusinessException;

    Ville modifierVille(Long idVille, Ville villeModifiee) throws BusinessException;

    void supprimerVille(Long idVille);

    /**
     * --- Méthode de validation métier ---
     */
    default void validateVille(Ville ville) throws BusinessException {
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

    List<Ville> villesParNomPrefix(String prefix);

    List<Ville> villesPopulationMin(int min);

    List<Ville> villesPopulationBetween(int min, int max);

    List<Ville> topNVillesDepartement(Long departementId, int n);
}
