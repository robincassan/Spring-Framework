package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.beans.Ville;
import fr.diginamic.hello.validators.VilleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private VilleValidator villeValidator;

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
    public ResponseEntity<String> ajouterVille(@Valid @RequestBody Ville nouvelleVille, BindingResult result) {
        if (result.hasErrors()) {
            // Concaténer les erreurs en une seule chaîne
            String erreurs = result.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                    .orElse("Erreur de validation");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(erreurs);
        }
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
        villeModifiee.setId(id); // forcer l'id de la requête

        // Validation avec le Validator custom
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(villeModifiee, "ville");
        villeValidator.validate(villeModifiee, errors);

        if (errors.hasErrors()) {
            String erreurs = errors.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                    .orElse("Erreur de validation");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurs);
        }

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
