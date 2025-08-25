package fr.diginamic.tp6.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
/**
 * Entité JPA représentant un département.
 *
 * <p>Un département est caractérisé par un code unique, un nom
 * et une liste de villes qui lui sont rattachées.</p>
 *
 * <p>La relation entre {@link Departement} et {@link Ville} est
 * de type <strong>OneToMany</strong> : un département peut contenir
 * plusieurs villes, mais une ville appartient toujours à un seul
 * département.</p>
 */
@Entity
@Table(name = "departement")
public class Departement {
    /** Identifiant unique du département (clé primaire) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Code unique du département (exemple : "75" pour Paris) */
    @NotBlank(message = "Le code du département est obligatoire")
    private String code;
    /** Nom du département (exemple : "Paris") */
    @Column(nullable = true)
    private String nom;

    /**
     * Liste des villes appartenant à ce département.
     *
     * <p>Relation JPA : un département peut avoir plusieurs villes.
     * La suppression d’un département entraîne également la suppression
     * des villes associées (cascade + orphanRemoval).</p>
     */
    //Relation : un département a plusieurs villes
    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ville> villes = new ArrayList<>();

    /** Constructeur par défaut requis par JPA */
    public Departement(){}

    /**
     * Constructeur permettant d’initialiser un département avec son code et son nom.
     *
     * @param code code unique du département
     * @param nom  nom du département
     */
    public Departement(String code, String nom) {
        this.code = code;
        this.nom = nom;
    }

    // Getters & setters
    /** @return l’identifiant unique du département */
    public Long getId() { return id; }
    /** @param id identifiant unique du département */
    public void setId(Long id) { this.id = id; }
    /** @return le code unique du département */
    public String getCode() { return code; }
    /** @param code code unique du département */
    public void setCode(String code) { this.code = code; }
    /** @return le nom du département */
    public String getNom() { return nom; }
    /** @param nom nom du département */
    public void setNom(String nom) { this.nom = nom; }
    /** @return la liste des villes du département */
    public List<Ville> getVilles() { return villes; }
    /** @param villes liste des villes appartenant au département */
    public void setVilles(List<Ville> villes) { this.villes = villes; }
}
