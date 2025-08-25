package fr.diginamic.tp6.controller;

import fr.diginamic.tp6.dto.DepartementDTO;
import fr.diginamic.tp6.dto.VilleDTO;
import fr.diginamic.tp6.model.Departement;
import fr.diginamic.tp6.model.Ville;
import fr.diginamic.tp6.service.DepartementService;
import fr.diginamic.tp6.service.VilleService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementController {

    private final DepartementService departementService;
    private final VilleService villeService;

    public DepartementController(DepartementService departementService, VilleService villeService) {
        this.departementService = departementService;
        this.villeService = villeService;
    }

    // Récupérer tous les départements (toujours paginé)
    @GetMapping
    public Page<DepartementDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return departementService.extractDepartements(page, size)
                .map(DepartementDTO::new);
    }

    // Récupérer un département par ID
    @GetMapping("/{id}")
    public ResponseEntity<DepartementDTO> getById(@PathVariable Long id) {
        Departement dept = departementService.extractDepartement(id);
        if (dept == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DepartementDTO(dept));
    }

    // Créer un département
    @PostMapping
    public ResponseEntity<DepartementDTO> create(@RequestBody Departement departement) {
        Departement saved = departementService.insertDepartement(departement);
        return ResponseEntity.ok(new DepartementDTO(saved));
    }

    // Mettre à jour un département
    @PutMapping("/{id}")
    public ResponseEntity<DepartementDTO> update(@PathVariable Long id, @RequestBody Departement departement) {
        Departement updated = departementService.modifierDepartement(id, departement);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DepartementDTO(updated));
    }

    // Supprimer un département
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Departement dept = departementService.extractDepartement(id);
        if (dept == null) return ResponseEntity.notFound().build();
        departementService.supprimerDepartement(id);
        return ResponseEntity.noContent().build();
    }

    // Lister les villes d’un département
    @GetMapping("/{id}/villes")
    public ResponseEntity<List<VilleDTO>> getVilles(@PathVariable Long id) {
        List<Ville> villes = departementService.villesParDepartement(id);
        if (villes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(villes.stream().map(VilleDTO::new).toList());
    }

    // Lister les n plus grandes villes d’un département
    @GetMapping("/{id}/villes/top")
    public ResponseEntity<List<VilleDTO>> getTopVilles(
            @PathVariable Long id,
            @RequestParam int n) {

        List<Ville> topVilles = departementService.nPlusGrandesVilles(id, n);
        if (topVilles.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(topVilles.stream().map(VilleDTO::new).toList());
    }

    // Lister les villes par population min/max
    @GetMapping("/{id}/villes/filter")
    public ResponseEntity<List<VilleDTO>> getVillesByPopulation(
            @PathVariable Long id,
            @RequestParam int min,
            @RequestParam int max) {

        List<Ville> villes = departementService.villesParPopulation(id, min, max);
        if (villes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(villes.stream().map(VilleDTO::new).toList());
    }
}
