package fr.diginamic.hello.validators;

import fr.diginamic.hello.beans.Ville;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VilleValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz){
        return Ville.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Ville ville = (Ville) target;

        if (ville.getId() <= 0) {
            errors.rejectValue("id", "id.positif", "L'id doit être strictement positif");
        }

        if (ville.getNom() == null || ville.getNom().length() < 2) {
            errors.rejectValue("nom", "nom.invalide", "Le nom doit contenir au moins 2 caractères");
        }

        if (ville.getNbHabitants() < 1) {
            errors.rejectValue("nbHabitants", "habitants.min", "Le nombre d'habitants doit être >= 1");
        }
    }
}
