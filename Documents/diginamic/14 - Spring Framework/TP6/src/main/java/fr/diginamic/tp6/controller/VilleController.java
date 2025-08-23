package fr.diginamic.tp6.controller;

import fr.diginamic.tp6.dto.VilleDTO;
import fr.diginamic.tp6.model.Ville;
import fr.diginamic.tp6.repository.VilleRepository;
import fr.diginamic.tp6.repository.DepartementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controleur REST permettant de gérer les opérations CRUD sur les villes
 * Endpoints disponibles :
 * - GET /villes : récupérer toutes les villes
 * - GET /villes/{id} : récupérer une ville par son identifiant
 * - GET /villes/search?nom=... : recherche une ville par son nom
 * - POST /villes : créer une nouvelle ville (avec rattachement obligatoire à un département existant)
 * - PUT /villes/{id} : mettre à jour une ville existante
 * - DELETE /villes/{id} : supprimer une ville
 * - GET /villes/departement/{departementId} : lister les villes d'un département
 * - GET /villes/departement/{departementId}/filter?min=...&max=... : lister les villes d'un departement filtrees par population
 */
@RestController
@RequestMapping("/villes")
public class VilleController {

    private final VilleRepository villeRepository;
    private final DepartementRepository departementRepository;

    /**
     * Constructeur permettant d’injecter les dépendances.
     *
     * @param villeRepository repository pour accéder aux données des villes
     * @param departementRepository repository pour accéder aux données des départements
     */
    public VilleController(VilleRepository villeRepository, DepartementRepository departementRepository) {
        this.villeRepository = villeRepository;
        this.departementRepository = departementRepository;
    }

    /**
     * Récupère toutes les villes.
     *
     * @return une liste de {@link VilleDTO}
     */
    @GetMapping
    public List<VilleDTO> getAll() {
        return villeRepository.findAll()
                .stream()
                .map(VilleDTO::new)
                .toList();
    }

    /**
     * Récupère une ville par son identifiant.
     *
     * @param id identifiant de la ville
     * @return la {@link VilleDTO} correspondante ou 404 si non trouvée
     */
    @GetMapping("/{id}")
    public ResponseEntity<VilleDTO> getById(@PathVariable Long id) {
        return villeRepository.findById(id)
                .map(VilleDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Recherche une ville par son nom.
     *
     * @param nom nom de la ville
     * @return la {@link VilleDTO} correspondante ou 404 si non trouvée
     */
    @GetMapping("/search")
    public ResponseEntity<VilleDTO> getByNom(@RequestParam String nom) {
        return villeRepository.findByNom(nom)
                .map(VilleDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle ville et l’associe à un département existant.
     *
     * @param ville entité {@link Ville} à enregistrer
     * @return la {@link VilleDTO} créée ou 400 si le département est invalide
     */
    @PostMapping
    public ResponseEntity<VilleDTO> create(@RequestBody Ville ville) {
        if (ville.getDepartement() == null ||
                ville.getDepartement().getId() == null ||
                !departementRepository.existsById(ville.getDepartement().getId())) {
            return ResponseEntity.badRequest().build();
        }
        Ville saved = villeRepository.save(ville);
        return ResponseEntity.ok(new VilleDTO(saved));
    }

    /**
     * Met à jour une ville existante.
     *
     * @param id identifiant de la ville à mettre à jour
     * @param ville données mises à jour
     * @return la {@link VilleDTO} mise à jour ou 404 si la ville n’existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<VilleDTO> update(@PathVariable Long id, @RequestBody Ville ville) {
        return villeRepository.findById(id).map(existing -> {
            existing.setNom(ville.getNom());
            existing.setPopulation(ville.getPopulation());
            existing.setCodePostal(ville.getCodePostal());

            if (ville.getDepartement() != null) {
                departementRepository.findById(ville.getDepartement().getId())
                        .ifPresent(existing::setDepartement);
            }

            Ville updated = villeRepository.save(existing);
            return ResponseEntity.ok(new VilleDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprime une ville par son identifiant.
     *
     * @param id identifiant de la ville
     * @return 204 si la suppression est réussie, 404 si la ville n’existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!villeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        villeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupère toutes les villes d’un département donné.
     *
     * @param departementId identifiant du département
     * @return liste de {@link VilleDTO} ou 404 si le département n’existe pas
     */
    // Lister les villes d’un département
    @GetMapping("/departement/{departementId}")
    public ResponseEntity<List<VilleDTO>> getVillesByDepartement(@PathVariable Long departementId) {
        if (!departementRepository.existsById(departementId)) {
            return ResponseEntity.notFound().build();
        }
        List<VilleDTO> villes = villeRepository.findByDepartementId(departementId)
                .stream()
                .map(VilleDTO::new)
                .toList();
        return ResponseEntity.ok(villes);
    }

    /**
     * Récupère toutes les villes d’un département dont la population
     * est comprise entre deux valeurs données.
     *
     * @param departementId identifiant du département
     * @param min population minimale
     * @param max population maximale
     * @return liste de {@link VilleDTO} filtrée ou 404 si le département n’existe pas
     */
    // Lister les villes d’un département avec population entre min et max
    @GetMapping("/departement/{departementId}/filter")
    public ResponseEntity<List<VilleDTO>> getVillesByDepartementAndPopulation(
            @PathVariable Long departementId,
            @RequestParam int min,
            @RequestParam int max) {
        if (!departementRepository.existsById(departementId)) {
            return ResponseEntity.notFound().build();
        }
        List<VilleDTO> villes = villeRepository.findByDepartementIdAndPopulationBetween(departementId, min, max)
                .stream()
                .map(VilleDTO::new)
                .toList();
        return ResponseEntity.ok(villes);
    }
}
