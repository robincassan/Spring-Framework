package fr.diginamic.tp9.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import fr.diginamic.tp9.dto.DepartementDTO;
import fr.diginamic.tp9.dto.VilleDTO;
import fr.diginamic.tp9.model.Departement;
import fr.diginamic.tp9.model.Ville;
import fr.diginamic.tp9.service.IDepartementService;
import fr.diginamic.tp9.service.IVilleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/departements")
public class DepartementController implements IDepartementController {

    private final IDepartementService departementService;
    private final IVilleService villeService;

    public DepartementController(IDepartementService departementService, IVilleService villeService) {
        this.departementService = departementService;
        this.villeService = villeService;
    }

    @Override
    @GetMapping
    public Page<DepartementDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return departementService.extractDepartements(page, size)
                .map(DepartementDTO::new);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DepartementDTO> getById(@PathVariable Long id) {
        Departement dept = departementService.extractDepartement(id);
        if (dept == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DepartementDTO(dept));
    }

    @Override
    @PostMapping
    public ResponseEntity<DepartementDTO> create(@RequestBody Departement departement) {
        Departement saved = departementService.insertDepartement(departement);
        return ResponseEntity.ok(new DepartementDTO(saved));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<DepartementDTO> update(@PathVariable Long id, @RequestBody Departement departement) {
        Departement updated = departementService.modifierDepartement(id, departement);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DepartementDTO(updated));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Departement dept = departementService.extractDepartement(id);
        if (dept == null) return ResponseEntity.notFound().build();
        departementService.supprimerDepartement(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{id}/villes")
    public ResponseEntity<List<VilleDTO>> getVilles(@PathVariable Long id) {
        List<Ville> villes = departementService.villesParDepartement(id);
        if (villes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(villes.stream().map(VilleDTO::new).toList());
    }

    @Override
    @GetMapping("/{id}/villes/top")
    public ResponseEntity<List<VilleDTO>> getTopVilles(@PathVariable Long id, @RequestParam int n) {
        List<Ville> topVilles = departementService.nPlusGrandesVilles(id, n);
        if (topVilles.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(topVilles.stream().map(VilleDTO::new).toList());
    }

    @Override
    @GetMapping("/{id}/villes/filter")
    public ResponseEntity<List<VilleDTO>> getVillesByPopulation(@PathVariable Long id, @RequestParam int min, @RequestParam int max) {
        List<Ville> villes = departementService.villesParPopulation(id, min, max);
        if (villes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(villes.stream().map(VilleDTO::new).toList());
    }

    // ================= Export PDF =================
    // imports : com.itextpdf.text.* et jakarta.servlet.http.HttpServletResponse sont ok

    @GetMapping("/export/{id}/pdf")
    public void exportDepartementPdf(@PathVariable Long id, HttpServletResponse response)
            throws IOException, DocumentException {
        Departement departement = departementService.extractDepartement(id);
        if (departement == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Département introuvable");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=departement_" + departement.getCode() + ".pdf");

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("Département: " + departement.getNom()));
            document.add(new Paragraph("Code: " + departement.getCode()));
            document.add(new Paragraph("\nListe des villes:"));

            List<Ville> villes = departementService.villesParDepartement(id);
            if (villes == null || villes.isEmpty()) {
                document.add(new Paragraph("Aucune ville disponible."));
            } else {
                // <-- utilisation d'une boucle for classique (permets de propager DocumentException)
                for (Ville ville : villes) {
                    document.add(new Paragraph(ville.getNom() + " - Population: " + ville.getPopulation()));
                }
            }
        } finally {
            // s'assure de fermer le document même en cas d'erreur
            if (document != null) {
                document.close();
            }
        }
    }
}



