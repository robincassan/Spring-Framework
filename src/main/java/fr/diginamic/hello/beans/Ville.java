package fr.diginamic.hello.beans;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class Ville {
    @Positive(message = "L'id doit être strictement positif")
    private int id;

    @NotNull(message = "Le nom ne peut pas être nul")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères")
    private String nom;

    @Min(value = 1, message = "Le nombre d'habitants doit être >= 1")
    private int nbHabitants;

    public Ville(){
    }

    //Constructeur

    /**
     *
     * @param id l'identifiant unique de la ville
     * @param nom Le nom de la ville
     * @param nbHabitants Le nombre d'habitants
     */
    public Ville(int id, String nom, int nbHabitants) {
        this.id = id;
        this.nom = nom;
        this.nbHabitants = nbHabitants;
    }

    //Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return Retourne le nom de la ville
     */
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbHabitants() {
        return nbHabitants;
    }

    public void setNbHabitants(int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }
}
