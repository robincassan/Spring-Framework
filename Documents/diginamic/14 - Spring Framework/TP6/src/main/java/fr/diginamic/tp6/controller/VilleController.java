package fr.diginamic.tp6.controller;

import fr.diginamic.tp6.dto.VilleDTO;
import fr.diginamic.tp6.model.Ville;
import fr.diginamic.tp6.service.VilleService;
import fr.diginamic.tp6.service.DepartementService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {

    private final VilleService villeService;
    private final DepartementService departementService;

    public VilleController(VilleService villeService, DepartementService departementService) {
        this.villeService = villeService;
        this.departementService = departementService;
    }

    // Récupérer toutes les villes (toujours paginé)
    @GetMapping
    public Page<VilleDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return villeService.extractVilles(page, size)
                .map(VilleDTO::new);
    }

    // Récupérer une ville par ID
    @GetMapping("/{id}")
    public ResponseEntity<VilleDTO> getById(@PathVariable Long id) {
        Ville ville = villeService.extractVille(id);
        if (ville == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new VilleDTO(ville));
    }

    // Recherche par nom
    @GetMapping("/search")
    public ResponseEntity<VilleDTO> getByNom(@RequestParam String nom) {
        Ville ville = villeService.extractVille(nom);
        if (ville == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new VilleDTO(ville));
    }

    // Créer une ville
    @PostMapping
    public ResponseEntity<VilleDTO> create(@RequestBody Ville ville) {
        if (ville.getDepartement() == null || ville.getDepartement().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Ville saved = villeService.insertVille(ville);
        return ResponseEntity.ok(new VilleDTO(saved));
    }

    // Mettre à jour une ville
    @PutMapping("/{id}")
    public ResponseEntity<VilleDTO> update(@PathVariable Long id, @RequestBody Ville ville) {
        Ville updated = villeService.modifierVille(id, ville);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new VilleDTO(updated));
    }

    // Supprimer une ville
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Ville ville = villeService.extractVille(id);
        if (ville == null) return ResponseEntity.notFound().build();
        villeService.supprimerVille(id);
        return ResponseEntity.noContent().build();
    }

    // Villes d’un département
    @GetMapping("/departement/{departementId}")
    public ResponseEntity<List<VilleDTO>> getVillesByDepartement(@PathVariable Long departementId) {
        List<Ville> villes = departementService.villesParDepartement(departementId);
        if (villes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(villes.stream().map(VilleDTO::new).toList());
    }

    // Villes d’un département filtrées par population
    @GetMapping("/departement/{departementId}/filter")
    public ResponseEntity<List<VilleDTO>> getVillesByDepartementAndPopulation(
            @PathVariable Long departementId,
            @RequestParam int min,
            @RequestParam int max) {

        List<Ville> villes = departementService.villesParPopulation(departementId, min, max);
        if (villes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(villes.stream().map(VilleDTO::new).toList());
    }

    // Recherche par préfixe
    @GetMapping("/search-prefix")
    public List<VilleDTO> getVillesByPrefix(@RequestParam String prefix) {
        return villeService.villesParNomPrefix(prefix).stream().map(VilleDTO::new).toList();
    }

    // Villes avec population min
    @GetMapping("/search-pop-min")
    public List<VilleDTO> getVillesByPopulationMin(@RequestParam int min) {
        return villeService.villesPopulationMin(min).stream().map(VilleDTO::new).toList();
    }

    // Villes avec population entre min et max
    @GetMapping("/search-pop-between")
    public List<VilleDTO> getVillesByPopulationBetween(@RequestParam int min, @RequestParam int max) {
        return villeService.villesPopulationBetween(min, max).stream().map(VilleDTO::new).toList();
    }
}
