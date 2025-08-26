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

    List<Ville> villesParNomPrefix(String prefix);

    List<Ville> villesPopulationMin(int min);

    List<Ville> villesPopulationBetween(int min, int max);

    List<Ville> topNVillesDepartement(Long departementId, int n);
}
