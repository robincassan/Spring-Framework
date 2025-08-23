package fr.diginamic.tp6;

import fr.diginamic.tp6.dao.DepartementDao;
import fr.diginamic.tp6.dao.VilleDao;
import fr.diginamic.tp6.model.Departement;
import fr.diginamic.tp6.model.Ville;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Composant Spring chargé d'initialiser les données de la base au démarrage.
 *
 * <p>Cette classe implémente {@link CommandLineRunner} et insère
 * un ensemble de départements et de villes prédéfinis si la base
 * de données est vide.</p>
 *
 * <p>Elle utilise les DAO {@link DepartementDao} et {@link VilleDao}
 * pour persister les entités.</p>
 *
 * <p>Lors de l'exécution, si la base contient déjà des départements,
 * l'initialisation est ignorée.</p>
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final DepartementDao departementDao;
    private final VilleDao villeDao;

    /**
     * Constructeur avec injection des DAO nécessaires.
     *
     * @param departementDao DAO pour gérer les départements
     * @param villeDao DAO pour gérer les villes
     */
    public DataInitializer(DepartementDao departementDao, VilleDao villeDao) {
        this.departementDao = departementDao;
        this.villeDao = villeDao;
    }

    /**
     * Méthode exécutée au démarrage de l'application.
     * <p>
     * Elle insère 10 départements et 10 villes si la base est vide.
     *
     * @param args arguments de la ligne de commande (non utilisés)
     * @throws Exception si une erreur survient lors de l'insertion
     */
    @Override
    public void run(String... args) throws Exception {
        // si déjà initialisé -> rien faire
        if (!departementDao.findAll().isEmpty()) {
            System.out.println("Seed skipped — départements déjà présents");
            return;
        }

        // --- Création départements (code, nom) ---
        Departement d75 = new Departement(); d75.setCode("75"); d75.setNom("Paris"); departementDao.save(d75);
        Departement d13 = new Departement(); d13.setCode("13"); d13.setNom("Bouches-du-Rhône"); departementDao.save(d13);
        Departement d69 = new Departement(); d69.setCode("69"); d69.setNom("Rhône"); departementDao.save(d69);
        Departement d31 = new Departement(); d31.setCode("31"); d31.setNom("Haute-Garonne"); departementDao.save(d31);
        Departement d06 = new Departement(); d06.setCode("06"); d06.setNom("Alpes-Maritimes"); departementDao.save(d06);
        Departement d44 = new Departement(); d44.setCode("44"); d44.setNom("Loire-Atlantique"); departementDao.save(d44);
        Departement d67 = new Departement(); d67.setCode("67"); d67.setNom("Bas-Rhin"); departementDao.save(d67);
        Departement d34 = new Departement(); d34.setCode("34"); d34.setNom("Hérault"); departementDao.save(d34);
        Departement d33 = new Departement(); d33.setCode("33"); d33.setNom("Gironde"); departementDao.save(d33);
        Departement d59 = new Departement(); d59.setCode("59"); d59.setNom("Nord"); departementDao.save(d59);

        // --- Création villes et rattachement au département approprié ---
        Ville paris = new Ville("Paris", 2148327, "75000"); paris.setDepartement(d75); villeDao.save(paris);
        Ville marseille = new Ville("Marseille", 861635, "13000"); marseille.setDepartement(d13); villeDao.save(marseille);
        Ville lyon = new Ville("Lyon", 518635, "69000"); lyon.setDepartement(d69); villeDao.save(lyon);
        Ville toulouse = new Ville("Toulouse", 493465, "31000"); toulouse.setDepartement(d31); villeDao.save(toulouse);
        Ville nice = new Ville("Nice", 341032, "06000"); nice.setDepartement(d06); villeDao.save(nice);
        Ville nantes = new Ville("Nantes", 314138, "44000"); nantes.setDepartement(d44); villeDao.save(nantes);
        Ville strasbourg = new Ville("Strasbourg", 280966, "67000"); strasbourg.setDepartement(d67); villeDao.save(strasbourg);
        Ville montpellier = new Ville("Montpellier", 295542, "34000"); montpellier.setDepartement(d34); villeDao.save(montpellier);
        Ville bordeaux = new Ville("Bordeaux", 257068, "33000"); bordeaux.setDepartement(d33); villeDao.save(bordeaux);
        Ville lille = new Ville("Lille", 234475, "59000"); lille.setDepartement(d59); villeDao.save(lille);

        System.out.println(" Seed: 10 villes et 10 départements insérés");
    }
}

