package fr.diginamic.tp6.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ville {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // identifiant auto-incrémenté

    @NotBlank(message = "Le nom de la ville est obligatoire")
    private String nom;

    private int population;

    private String codePostal;

    // Constructeur sans paramètre requis par JPA
    public Ville() {
    }

    // Constructeur pratique avec paramètres
    public Ville(String nom, int population, String codePostal) {
        this.nom = nom;
        this.population = population;
        this.codePostal = codePostal;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", population=" + population +
                ", codePostal='" + codePostal + '\'' +
                '}';
    }
}
