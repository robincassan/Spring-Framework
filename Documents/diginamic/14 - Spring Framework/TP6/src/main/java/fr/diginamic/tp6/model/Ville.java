package fr.diginamic.tp6.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Entité JPA représentant une ville.
 *
 * <p>Une ville est caractérisée par un nom, une population,
 * un code postal, et elle appartient à un {@link Departement}.</p>
 *
 * <p>La relation entre {@link Ville} et {@link Departement} est de type
 * <strong>ManyToOne</strong> : plusieurs villes peuvent appartenir au
 * même département.</p>
 */
@Entity
@Table(name = "ville")
public class Ville {

    /** Identifiant unique de la ville (clé primaire, auto-incrémentée) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // identifiant auto-incrémenté

    /** Nom de la ville (obligatoire) */
    @NotBlank(message = "Le nom de la ville est obligatoire")
    private String nom;

    /** Nombre d’habitants de la ville */
    private int population;

    /** Code postal de la ville */
    private String codePostal;

    // ---- Relation ManyToOne avec Departement ----
    /**
     * Département auquel appartient la ville.
     *
     * <p>Relation JPA : plusieurs villes peuvent être rattachées à un
     * même département (ManyToOne).</p>
     */
    @ManyToOne
    @JoinColumn(name = "departement_id", nullable = true)
    private Departement departement;

    /** @return le département auquel appartient la ville */
    public Departement getDepartement() { return departement; }

    /** @param departement département auquel rattacher la ville */
    public void setDepartement(Departement departement) { this.departement = departement; }

    // Constructeur sans paramètre requis par JPA
    /** Constructeur sans paramètre requis par JPA */
    public Ville() {
    }

    // Constructeur pratique avec paramètres
    /**
     * Constructeur pratique pour créer une ville avec ses informations principales.
     *
     * @param nom nom de la ville
     * @param population nombre d’habitants
     * @param codePostal code postal
     */
    public Ville(String nom, int population, String codePostal) {
        this.nom = nom;
        this.population = population;
        this.codePostal = codePostal;
    }

    // Getters et Setters
    /** @return identifiant unique de la ville */
    public Long getId() {
        return id;
    }

    /** @param id identifiant unique de la ville */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return le nom de la ville */
    public String getNom() {
        return nom;
    }

    /** @param nom nom de la ville */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /** @return la population de la ville */
    public int getPopulation() {
        return population;
    }

    /** @param population population de la ville */
    public void setPopulation(int population) {
        this.population = population;
    }

    /** @return le code postal de la ville */
    public String getCodePostal() {
        return codePostal;
    }

    /** @param codePostal code postal de la ville */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Retourne une représentation textuelle de la ville.
     *
     * @return chaîne de caractères contenant les informations principales de la ville
     */
    @Override
    public String toString() {
        return "Ville{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", population=" + population +
                ", codePostal='" + codePostal + '\'' +
                ", departement=" + (departement != null ? departement.getNom() : "null") +
                '}';
    }
}
