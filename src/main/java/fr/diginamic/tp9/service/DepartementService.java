package fr.diginamic.tp9.service;

import fr.diginamic.tp9.exception.BusinessException;
import fr.diginamic.tp9.model.Departement;
import fr.diginamic.tp9.model.Ville;
import fr.diginamic.tp9.repository.DepartementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService implements IDepartementService {

    private final DepartementRepository departementRepository;

    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    /** 🔹 Récupérer tous les départements (toujours paginé) */
    @Override
    public Page<Departement> extractDepartements(int page, int size) {
        return departementRepository.findAll(PageRequest.of(page, size));
    }

    /**  Récupérer un département par ID */
    @Override
    public Departement extractDepartement(Long id) {
        return departementRepository.findById(id).orElse(null);
    }

    @Override
    public Departement insertDepartement(Departement departement) throws BusinessException {
        validateDepartement(departement);
        return departementRepository.save(departement);
    }

    @Override
    public Departement modifierDepartement(Long id, Departement departementModifie) throws BusinessException {
        Departement existing = departementRepository.findById(id).orElseThrow(() -> new BusinessException("Département non trouvé"));
        validateDepartement(departementModifie);
        existing.setNom(departementModifie.getNom());
        existing.setCode(departementModifie.getCode());
        return departementRepository.save(existing);
    }

    /**  Supprimer un département */
    @Override
    public void supprimerDepartement(Long id) {
        departementRepository.findById(id).ifPresent(departementRepository::delete);
    }

    /**  Lister les n plus grandes villes d’un département */
    @Override
    public List<Ville> nPlusGrandesVilles(Long idDept, int n) {
        Departement d = departementRepository.findById(idDept).orElse(null);
        if (d == null) return List.of();
        return d.getVilles().stream()
                .sorted((v1, v2) -> Integer.compare(v2.getPopulation(), v1.getPopulation()))
                .limit(n)
                .toList();
    }

    /**  Lister les villes avec population min/max dans un département */
    @Override
    public List<Ville> villesParPopulation(Long idDept, int min, int max) {
        Departement d = departementRepository.findById(idDept).orElse(null);
        if (d == null) return List.of();
        return d.getVilles().stream()
                .filter(v -> v.getPopulation() >= min && v.getPopulation() <= max)
                .toList();
    }

    @Override
    public List<Ville> villesParDepartement(Long departementId) {
        return departementRepository.findById(departementId)
                .map(Departement::getVilles)
                .orElse(List.of());
    }
}
