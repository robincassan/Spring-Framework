package fr.diginamic.tp9.dto;

import fr.diginamic.tp9.model.Ville;

/**
 * Data Transfer Object (DTO) pour l'entité {@link Ville}.
 *
 * <p>Ce DTO expose les informations essentielles d'une ville
 * tout en simplifiant la représentation de son département associé.</p>
 *
 * <p>Au lieu de renvoyer l'entité {@code Departement}, on expose
 * uniquement son nom sous forme de {@link String} pour éviter la
 * surcharge d'informations et les problèmes de sérialisation liés
 * aux relations JPA bidirectionnelles.</p>
 */
public class VilleDTO {
    /** Identifiant unique de la ville */
    private Long id;
    /** Nom de la ville */
    private String nom;
    /** Population de la ville */
    private int population;
    /** Code postal de la ville */
    private String codePostal;
    /** Nom du département auquel appartient la ville */
    private String departement; // ici on met juste le nom du département

    /**
     * Construit un DTO à partir d'une entité {@link Ville}.
     *
     * @param ville entité de type {@link Ville}
     */
    public VilleDTO(Ville ville) {
        this.id = ville.getId();
        this.nom = ville.getNom();
        this.population = ville.getPopulation();
        this.codePostal = ville.getCodePostal();
        this.departement = (ville.getDepartement() != null)
                ? ville.getDepartement().getNom()
                : "Département inconnu";
    }

    // Getters
    /** @return l'identifiant unique de la ville */
    public Long getId() { return id; }
    /** @return le nom de la ville */
    public String getNom() { return nom; }
    /** @return la population de la ville */
    public int getPopulation() { return population; }
    /** @return le code postal de la ville */
    public String getCodePostal() { return codePostal; }
    /** @return le nom du département auquel appartient la ville */
    public String getDepartement() { return departement; }
}

