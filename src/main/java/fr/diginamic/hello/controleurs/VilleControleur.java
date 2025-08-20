package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.beans.Ville;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    // Liste partagée entre GET et POST
    private List<Ville> villes = new ArrayList<>();

    // Constructeur pour initialiser quelques villes
    public VilleControleur() {
        villes.add(new Ville("Paris", 2148000));
        villes.add(new Ville("Lyon", 515695));
        villes.add(new Ville("Marseille", 861635));
        villes.add(new Ville("Toulouse", 479553));
    }

    // GET - récupérer la liste
    @GetMapping
    public List<Ville> getVilles() {
        return villes;
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


}
