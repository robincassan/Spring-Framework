package fr.diginamic.tp6.controller;

import fr.diginamic.tp6.model.Ville;
import fr.diginamic.tp6.service.VilleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {

    private final VilleService villeService;

    public VilleController(VilleService villeService) {
        this.villeService = villeService;
    }

    //  1) GET toutes les villes
    @GetMapping
    public List<Ville> getAllVilles() {
        return villeService.extractVilles();
    }

    //  2) GET une ville par id
    @GetMapping("/{id}")
    public Ville getVilleById(@PathVariable Long id) {
        return villeService.extractVille(id);
    }

    //  3) GET une ville par nom
    @GetMapping("/search")
    public Ville getVilleByNom(@RequestParam String nom) {
        return villeService.extractVille(nom);
    }

    //  4) POST → ajouter une nouvelle ville
    @PostMapping
    public List<Ville> addVille(@RequestBody Ville ville) {
        return villeService.insertVille(ville);
    }

    //  5) PUT → modifier une ville existante
    @PutMapping("/{id}")
    public List<Ville> updateVille(@PathVariable Long id, @RequestBody Ville villeModifiee) {
        return villeService.modifierVille(id, villeModifiee);
    }

    // 6) DELETE → supprimer une ville
    @DeleteMapping("/{id}")
    public List<Ville> deleteVille(@PathVariable Long id) {
        return villeService.supprimerVille(id);
    }
}