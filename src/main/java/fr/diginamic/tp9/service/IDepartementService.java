package fr.diginamic.tp9.service;

import fr.diginamic.tp9.exception.BusinessException;
import fr.diginamic.tp9.model.Departement;
import fr.diginamic.tp9.model.Ville;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDepartementService {
    Page<Departement> extractDepartements(int page, int size);

    Departement extractDepartement(Long id);

    Departement insertDepartement(Departement departement) throws BusinessException;

    Departement modifierDepartement(Long id, Departement departementModifie) throws BusinessException;

    void supprimerDepartement(Long id);

    default void validateDepartement(Departement departement) throws BusinessException {
        if (departement.getNom() == null || departement.getNom().length() < 3) {
            throw new BusinessException("Le nom du département doit comporter au moins 3 lettres");
        }
        if (departementRepository.existsByNom(departement.getNom())) {
            throw new BusinessException("Le nom du département doit être unique");
        }
    }

    List<Ville> nPlusGrandesVilles(Long idDept, int n);

    List<Ville> villesParPopulation(Long idDept, int min, int max);

    List<Ville> villesParDepartement(Long departementId);
}
