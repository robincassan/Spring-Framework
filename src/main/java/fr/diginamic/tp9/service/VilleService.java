package fr.diginamic.tp9.service;

import fr.diginamic.tp9.exception.BusinessException;
import fr.diginamic.tp9.model.Ville;
import fr.diginamic.tp9.repository.VilleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService implements IVilleService {

    private final VilleRepository villeRepository;

    public VilleService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    /**  Récupérer toutes les villes avec pagination */
    @Override
    public Page<Ville> extractVilles(int page, int size) {
        return villeRepository.findAll(PageRequest.of(page, size));
    }

    /**  Récupérer une ville par son ID */
    @Override
    public Ville extractVille(Long idVille) {
        return villeRepository.findById(idVille).orElse(null);
    }

    /**  Récupérer une ville par son nom */
    @Override
    public Ville extractVille(String nom) {
        return villeRepository.findByNom(nom).orElse(null);
    }

    /**  Ajouter une nouvelle ville */
    @Override
    public Ville insertVille(Ville ville) throws BusinessException {
        validateVille(ville);
        return villeRepository.save(ville);
    }

    /**  Modifier une ville existante */
    @Override
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
    @Override
    public void supprimerVille(Long idVille) {
        villeRepository.findById(idVille).ifPresent(villeRepository::delete);
    }

    /**  Rechercher les villes dont le nom commence par un préfixe */
    @Override
    public List<Ville> villesParNomPrefix(String prefix) {
        return villeRepository.findAll().stream()
                .filter(v -> v.getNom().startsWith(prefix))
                .toList();
    }

    /**  Rechercher les villes dont la population est supérieure à min */
    @Override
    public List<Ville> villesPopulationMin(int min) {
        return villeRepository.findAll().stream()
                .filter(v -> v.getPopulation() > min)
                .sorted((v1, v2) -> Integer.compare(v2.getPopulation(), v1.getPopulation()))
                .toList();
    }

    /**  Rechercher les villes dont la population est entre min et max */
    @Override
    public List<Ville> villesPopulationBetween(int min, int max) {
        return villeRepository.findAll().stream()
                .filter(v -> v.getPopulation() > min && v.getPopulation() < max)
                .sorted((v1, v2) -> Integer.compare(v2.getPopulation(), v1.getPopulation()))
                .toList();
    }

    /**  Rechercher les n villes les plus peuplées d’un département */
    @Override
    public List<Ville> topNVillesDepartement(Long departementId, int n) {
        return villeRepository.findByDepartementIdOrderByPopulationDesc(departementId, PageRequest.of(0, n));
    }

    /**
     * --- Méthode de validation métier ---
     */
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
}
