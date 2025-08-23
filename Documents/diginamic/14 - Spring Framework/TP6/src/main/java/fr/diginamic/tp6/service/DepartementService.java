package fr.diginamic.tp6.service;

import fr.diginamic.tp6.dao.DepartementDao;
import fr.diginamic.tp6.model.Departement;
import fr.diginamic.tp6.model.Ville;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    private final DepartementDao departementDao;

    public DepartementService(DepartementDao departementDao) {
        this.departementDao = departementDao;
    }

    public List<Departement> extractDepartements() {
        return departementDao.findAll();
    }

    public Departement extractDepartement(Long id) {
        return departementDao.findById(id);
    }

    public List<Departement> insertDepartement(Departement departement) {
        departementDao.save(departement);
        return extractDepartements();
    }

    public List<Departement> modifierDepartement(Long id, Departement departementModifie) {
        Departement d = departementDao.findById(id);
        if (d != null) {
            d.setNom(departementModifie.getNom());
            d.setCode(departementModifie.getCode());
            departementDao.update(d);
        }
        return extractDepartements();
    }

    public List<Departement> supprimerDepartement(Long id) {
        Departement d = departementDao.findById(id);
        if (d != null) {
            departementDao.delete(d);
        }
        return extractDepartements();
    }

    /** 🔹 Lister les n plus grandes villes d’un département */
    public List<Ville> nPlusGrandesVilles(Long idDept, int n) {
        Departement d = departementDao.findById(idDept);
        if (d == null) return List.of();
        return d.getVilles().stream()
                .sorted((v1, v2) -> Integer.compare(v2.getPopulation(), v1.getPopulation()))
                .limit(n)
                .toList();
    }

    /** 🔹 Lister les villes avec population min/max dans un département */
    public List<Ville> villesParPopulation(Long idDept, int min, int max) {
        Departement d = departementDao.findById(idDept);
        if (d == null) return List.of();
        return d.getVilles().stream()
                .filter(v -> v.getPopulation() >= min && v.getPopulation() <= max)
                .toList();
    }
}
