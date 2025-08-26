package fr.diginamic.tp9.controller;

import fr.diginamic.tp9.dto.DepartementDTO;
import fr.diginamic.tp9.dto.VilleDTO;
import fr.diginamic.tp9.model.Departement;
import fr.diginamic.tp9.model.Ville;
import fr.diginamic.tp9.service.DepartementService;
import fr.diginamic.tp9.service.IDepartementService;
import fr.diginamic.tp9.service.IVilleService;
import fr.diginamic.tp9.service.VilleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementController implements IDepartementController {

    private final IDepartementService departementService;
    private final IVilleService villeService;

    public DepartementController(IDepartementService departementService, IVilleService villeService) {
        this.departementService = departementService;
        this.villeService = villeService;
    }

    @Override
    @GetMapping
    public Page<DepartementDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return departementService.extractDepartements(page, size)
                .map(DepartementDTO::new);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DepartementDTO> getById(@PathVariable Long id) {
        Departement dept = departementService.extractDepartement(id);
        if (dept == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DepartementDTO(dept));
    }

    @Override
    @PostMapping
    public ResponseEntity<DepartementDTO> create(@RequestBody Departement departement) {
        Departement saved = departementService.insertDepartement(departement);
        return ResponseEntity.ok(new DepartementDTO(saved));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<DepartementDTO> update(@PathVariable Long id, @RequestBody Departement departement) {
        Departement updated = departementService.modifierDepartement(id, departement);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DepartementDTO(updated));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Departement dept = departementService.extractDepartement(id);
        if (dept == null) return ResponseEntity.notFound().build();
        departementService.supprimerDepartement(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{id}/villes")
    public ResponseEntity<List<VilleDTO>> getVilles(@PathVariable Long id) {
        List<Ville> villes = departementService.villesParDepartement(id);
        if (villes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(villes.stream().map(VilleDTO::new).toList());
    }

    @Override
    @GetMapping("/{id}/villes/top")
    public ResponseEntity<List<VilleDTO>> getTopVilles(@PathVariable Long id, @RequestParam int n) {
        List<Ville> topVilles = departementService.nPlusGrandesVilles(id, n);
        if (topVilles.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(topVilles.stream().map(VilleDTO::new).toList());
    }

    @Override
    @GetMapping("/{id}/villes/filter")
    public ResponseEntity<List<VilleDTO>> getVillesByPopulation(@PathVariable Long id, @RequestParam int min, @RequestParam int max) {
        List<Ville> villes = departementService.villesParPopulation(id, min, max);
        if (villes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(villes.stream().map(VilleDTO::new).toList());
    }
}

