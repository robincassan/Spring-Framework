package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.beans.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    //Méthode pour renvoyer une liste de villes
    @GetMapping
    public List<Ville> getVilles(){
        return Arrays.asList(
                new Ville("Paris", 2200000),
                new Ville("Lyon", 510000),
                new Ville("Marseille", 860000),
                new Ville("Toulouse", 480000)
        );
    }


}
