package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.beans.Ville;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    // Liste partagée entre GET et POST
    private List<Ville> villes = new ArrayList<>();
    private AtomicInteger compteurId = new AtomicInteger(1); // pour générer les ids

    // Constructeur pour initialiser quelques villes
    public VilleControleur() {
        villes.add(new Ville(compteurId.getAndIncrement(),"Paris", 2148000));
        villes.add(new Ville(compteurId.getAndIncrement(),"Lyon", 515695));
        villes.add(new Ville(compteurId.getAndIncrement(),"Marseille", 861635));
        villes.add(new Ville(compteurId.getAndIncrement(), "Toulouse", 479553));
    }

    // GET ALL - récupérer la liste
    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable int id){
        Optional<Ville> ville = villes.stream().filter(v -> v.getId() == id).findFirst();
        return ville.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    // POST - ajouter une nouvelle ville
    @PostMapping
    public ResponseEntity<String> ajouterVille(@RequestBody Ville nouvelleVille) {
        // Vérifier si la ville existe déjà
        boolean existe = villes.stream()
                .anyMatch(v -> v.getNom().equalsIgnoreCase(nouvelleVille.getNom()));

        if (existe) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La ville existe déjà");
        }

        // Ajouter la ville
        villes.add(nouvelleVille);
        return ResponseEntity.ok("Ville insérée avec succès");
    }
    // ----------------- PUT -----------------
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @RequestBody Ville villeModifiee) {
        Optional<Ville> villeOpt = villes.stream().filter(v -> v.getId() == id).findFirst();
        if (villeOpt.isPresent()) {
            Ville ville = villeOpt.get();
            ville.setNom(villeModifiee.getNom());
            ville.setNbHabitants(villeModifiee.getNbHabitants());
            return ResponseEntity.ok("Ville modifiée avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville introuvable");
        }
    }

    // ----------------- DELETE -----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille(@PathVariable int id) {
        boolean removed = villes.removeIf(v -> v.getId() == id);
        if (removed) {
            return ResponseEntity.ok("Ville supprimée avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ville introuvable");
        }
    }



}
