package fr.diginamic.tp6.service;

import fr.diginamic.tp6.model.Departement;
import fr.diginamic.tp6.model.Ville;
import fr.diginamic.tp6.repository.DepartementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    private final DepartementRepository departementRepository;

    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    /** 🔹 Récupérer tous les départements (toujours paginé) */
    public Page<Departement> extractDepartements(int page, int size) {
        return departementRepository.findAll(PageRequest.of(page, size));
    }

    /**  Récupérer un département par ID */
    public Departement extractDepartement(Long id) {
        return departementRepository.findById(id).orElse(null);
    }

    /**  Ajouter un département */
    public Departement insertDepartement(Departement departement) {
        return departementRepository.save(departement);
    }

    /**  Modifier un département */
    public Departement modifierDepartement(Long id, Departement departementModifie) {
        return departementRepository.findById(id).map(d -> {
            d.setNom(departementModifie.getNom());
            d.setCode(departementModifie.getCode());
            return departementRepository.save(d);
        }).orElse(null);
    }

    /**  Supprimer un département */
    public void supprimerDepartement(Long id) {
        departementRepository.findById(id).ifPresent(departementRepository::delete);
    }

    /**  Lister les n plus grandes villes d’un département */
    public List<Ville> nPlusGrandesVilles(Long idDept, int n) {
        Departement d = departementRepository.findById(idDept).orElse(null);
        if (d == null) return List.of();
        return d.getVilles().stream()
                .sorted((v1, v2) -> Integer.compare(v2.getPopulation(), v1.getPopulation()))
                .limit(n)
                .toList();
    }

    /**  Lister les villes avec population min/max dans un département */
    public List<Ville> villesParPopulation(Long idDept, int min, int max) {
        Departement d = departementRepository.findById(idDept).orElse(null);
        if (d == null) return List.of();
        return d.getVilles().stream()
                .filter(v -> v.getPopulation() >= min && v.getPopulation() <= max)
                .toList();
    }

    public List<Ville> villesParDepartement(Long departementId) {
        return departementRepository.findById(departementId)
                .map(Departement::getVilles)
                .orElse(List.of());
    }
}
