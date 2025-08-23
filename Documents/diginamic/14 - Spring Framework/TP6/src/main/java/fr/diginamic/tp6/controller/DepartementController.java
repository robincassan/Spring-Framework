package fr.diginamic.tp6.controller;

import fr.diginamic.tp6.dto.DepartementDTO;
import fr.diginamic.tp6.model.Departement;
import fr.diginamic.tp6.repository.DepartementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Contrôleur REST pour la gestion des départements.
 * <p>
 * Cette classe expose des endpoints permettant de :
 * <ul>
 *     <li>Lister tous les départements</li>
 *     <li>Consulter un département par son identifiant</li>
 *     <li>Créer un nouveau département</li>
 *     <li>Mettre à jour un département existant</li>
 *     <li>Supprimer un département</li>
 *     <li>Récupérer les villes d’un département donné</li>
 * </ul>
 *
 * @author Robin
 * @version 1.0
 */
@RestController
@RequestMapping("/departements")
public class DepartementController {

    private final DepartementRepository departementRepository;

    /**
     * Constructeur injectant le repository des départements.
     *
     * @param departementRepository repository permettant d’accéder aux données des départements
     */
    public DepartementController(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    /**
     * Récupère la liste de tous les départements.
     *
     * @return liste de {@link DepartementDTO}
     */
    @GetMapping
    public List<DepartementDTO> getAll() {
        return departementRepository.findAll()
                .stream()
                .map(DepartementDTO::new)
                .toList();
    }

    /**
     * Récupère un département par son identifiant.
     *
     * @param id identifiant du département recherché
     * @return {@link ResponseEntity} contenant le {@link DepartementDTO} si trouvé, sinon 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartementDTO> getById(@PathVariable Long id) {
        return departementRepository.findById(id)
                .map(DepartementDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée un nouveau département.
     * <p>
     * Si des villes sont fournies, elles sont automatiquement rattachées au département.
     *
     * @param departement département à créer
     * @return {@link ResponseEntity} contenant le {@link DepartementDTO} créé
     */
    @PostMapping
    public ResponseEntity<DepartementDTO> create(@RequestBody Departement departement) {
        // évite NullPointerException si aucune ville n’est envoyée
        if (departement.getVilles() != null) {
            // rattache chaque ville à ce département
            departement.getVilles().forEach(v -> v.setDepartement(departement));
        }

        Departement saved = departementRepository.save(departement);
        return ResponseEntity.ok(new DepartementDTO(saved));
    }

    /**
     * Met à jour un département existant.
     *
     * @param id identifiant du département à mettre à jour
     * @param departement objet contenant les nouvelles valeurs (nom et code)
     * @return {@link ResponseEntity} contenant le {@link DepartementDTO} mis à jour,
     * ou 404 Not Found si le département n’existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepartementDTO> update(@PathVariable Long id, @RequestBody Departement departement) {
        return departementRepository.findById(id).map(existing -> {
            existing.setNom(departement.getNom());
            existing.setCode(departement.getCode());
            Departement updated = departementRepository.save(existing);
            return ResponseEntity.ok(new DepartementDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprime un département par son identifiant.
     *
     * @param id identifiant du département à supprimer
     * @return {@link ResponseEntity} avec statut 204 No Content si supprimé,
     * ou 404 Not Found si inexistant
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!departementRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        departementRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupère la liste des noms de villes d’un département.
     *
     * @param id identifiant du département
     * @return {@link ResponseEntity} contenant la liste des noms de villes,
     * ou 404 Not Found si le département n’existe pas
     */
    @GetMapping("/{id}/villes")
    public ResponseEntity<List<String>> getVillesByDepartement(@PathVariable Long id) {
        return departementRepository.findById(id)
                .map(departement -> {
                    // On récupère juste les noms des villes pour éviter la boucle infinie
                    List<String> nomsVilles = departement.getVilles()
                            .stream()
                            .map(v -> v.getNom())
                            .toList();
                    return ResponseEntity.ok(nomsVilles);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
