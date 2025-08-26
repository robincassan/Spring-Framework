package fr.diginamic.tp9.controller;

import fr.diginamic.tp9.dto.VilleDTO;
import fr.diginamic.tp9.model.Ville;
import fr.diginamic.tp9.service.IVilleService;
import fr.diginamic.tp9.service.IDepartementService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController implements IVilleController {

    private final IVilleService villeService;
    private final IDepartementService departementService;

    public VilleController(IVilleService villeService, IDepartementService departementService) {
        this.villeService = villeService;
        this.departementService = departementService;
    }

    @Override
    @GetMapping
    public Page<VilleDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return villeService.extractVilles(page, size)
                .map(VilleDTO::new);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<VilleDTO> getById(@PathVariable Long id) {
        Ville ville = villeService.extractVille(id);
        if (ville == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new VilleDTO(ville));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<VilleDTO> getByNom(@RequestParam String nom) {
        Ville ville = villeService.extractVille(nom);
        if (ville == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new VilleDTO(ville));
    }

    @Override
    @PostMapping
    public ResponseEntity<VilleDTO> create(@RequestBody Ville ville) {
        if (ville.getDepartement() == null || ville.getDepartement().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Ville saved = villeService.insertVille(ville);
        return ResponseEntity.ok(new VilleDTO(saved));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<VilleDTO> update(@PathVariable Long id, @RequestBody Ville ville) {
        Ville updated = villeService.modifierVille(id, ville);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new VilleDTO(updated));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Ville ville = villeService.extractVille(id);
        if (ville == null) return ResponseEntity.notFound().build();
        villeService.supprimerVille(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/departement/{departementId}")
    public ResponseEntity<List<VilleDTO>> getVillesByDepartement(@PathVariable Long departementId) {
        List<Ville> villes = departementService.villesParDepartement(departementId);
        if (villes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(villes.stream().map(VilleDTO::new).toList());
    }

    @Override
    @GetMapping("/departement/{departementId}/filter")
    public ResponseEntity<List<VilleDTO>> getVillesByDepartementAndPopulation(@PathVariable Long departementId,
                                                                              @RequestParam int min,
                                                                              @RequestParam int max) {
        List<Ville> villes = departementService.villesParPopulation(departementId, min, max);
        if (villes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(villes.stream().map(VilleDTO::new).toList());
    }

    @Override
    @GetMapping("/search-prefix")
    public List<VilleDTO> getVillesByPrefix(@RequestParam String prefix) {
        return villeService.villesParNomPrefix(prefix).stream().map(VilleDTO::new).toList();
    }

    @Override
    @GetMapping("/search-pop-min")
    public List<VilleDTO> getVillesByPopulationMin(@RequestParam int min) {
        return villeService.villesPopulationMin(min).stream().map(VilleDTO::new).toList();
    }

    @Override
    @GetMapping("/search-pop-between")
    public List<VilleDTO> getVillesByPopulationBetween(@RequestParam int min, @RequestParam int max) {
        return villeService.villesPopulationBetween(min, max).stream().map(VilleDTO::new).toList();
    }
}
