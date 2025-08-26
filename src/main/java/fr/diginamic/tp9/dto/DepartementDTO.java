package fr.diginamic.tp9.dto;

import fr.diginamic.tp9.model.Departement;
import fr.diginamic.tp9.model.Ville;

import java.util.List;

/**
 * Data Transfer Object (DTO) pour l'entité {@link Departement}.
 *
 * <p>Ce DTO permet d'exposer uniquement les informations nécessaires
 * d'un département côté API, sans renvoyer directement les entités JPA.</p>
 *
 * <p>Les villes associées sont exposées uniquement sous forme de
 * {@link String} (leur nom), afin d'éviter la surcharge d'informations
 * et les problèmes de sérialisation liés aux relations bidirectionnelles.</p>
 */
public class DepartementDTO {
    /** Identifiant unique du département */
    private Long id;
    /** Code du département (ex: "34" pour l'Hérault) */
    private String code;
    /** Nom du département (ex: "Hérault") */
    private String nom;
    /** Liste des noms des villes appartenant au département */
    private List<String> villes; // on expose seulement les noms des villes

    /**
     * Construit un DTO à partir d'une entité {@link Departement}.
     *
     * @param departement entité de type {@link Departement}
     */
    // Constructeur à partir de l'entité
    public DepartementDTO(Departement departement) {
        this.id = departement.getId();
        this.code = departement.getCode();
        this.nom = departement.getNom();
        this.villes = departement.getVilles()
                .stream()
                .map(Ville::getNom)
                .toList();
    }

    //
    /** @return l'identifiant du département */
    public Long getId() { return id; }
    /** @return le code du département */
    public String getCode() { return code; }
    /** @return le nom du département */
    public String getNom() { return nom; }
    /** @return la liste des noms des villes du département */
    public List<String> getVilles() { return villes; }
}

