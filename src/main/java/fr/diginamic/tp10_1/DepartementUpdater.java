package fr.diginamic.tp10_1;

import fr.diginamic.tp9.repository.DepartementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DepartementUpdater implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();
    private final DepartementRepository departementRepository;

    public DepartementUpdater(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String url = "https://geo.api.gouv.fr/departements";
        DepartementApi[] departements = restTemplate.getForObject(url, DepartementApi[].class);

        if (departements != null) {
            for (DepartementApi depApi : departements) {
                // Recherche par code (nouvelle méthode)
                departementRepository.findByCode(depApi.getCode()).ifPresent(dep -> {
                    dep.setNom(depApi.getNom());
                    departementRepository.save(dep);
                });
            }
        }

        System.out.println("Mise à jour terminée !");
    }
}
