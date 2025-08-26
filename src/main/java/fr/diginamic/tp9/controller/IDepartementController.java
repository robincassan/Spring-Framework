package fr.diginamic.tp9.controller;

import fr.diginamic.tp9.dto.DepartementDTO;
import fr.diginamic.tp9.dto.VilleDTO;
import fr.diginamic.tp9.model.Departement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IDepartementController {
    @GetMapping
    @Operation(summary = "Retourne la liste paginée de tous les départements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des départements",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DepartementDTO.class))))
    })
    Page<DepartementDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    @Operation(summary = "Retourne un département par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département trouvé",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DepartementDTO.class))),
            @ApiResponse(responseCode = "404", description = "Département non trouvé", content = @Content())
    })
    ResponseEntity<DepartementDTO> getById(
            @Parameter(description = "ID du département", required = true, example = "34")
            @PathVariable Long id);

    @PostMapping
    @Operation(summary = "Crée un nouveau département")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département créé",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DepartementDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erreur de validation", content = @Content())
    })
    ResponseEntity<DepartementDTO> create(@RequestBody Departement departement);

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un département existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département mis à jour",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DepartementDTO.class))),
            @ApiResponse(responseCode = "404", description = "Département introuvable", content = @Content())
    })
    ResponseEntity<DepartementDTO> update(@PathVariable Long id, @RequestBody Departement departement);

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un département")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Département supprimé"),
            @ApiResponse(responseCode = "404", description = "Département introuvable", content = @Content())
    })
    ResponseEntity<Void> delete(@PathVariable Long id);

    @GetMapping("/{id}/villes")
    @Operation(summary = "Retourne toutes les villes d'un département")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des villes",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée", content = @Content())
    })
    ResponseEntity<List<VilleDTO>> getVilles(@PathVariable Long id);

    @GetMapping("/{id}/villes/top")
    @Operation(summary = "Retourne les N plus grandes villes d'un département")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des N plus grandes villes",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée", content = @Content())
    })
    ResponseEntity<List<VilleDTO>> getTopVilles(
            @PathVariable Long id,
            @Parameter(description = "Nombre de villes à retourner", required = true, example = "5")
            @RequestParam int n);

    @GetMapping("/{id}/villes/filter")
    @Operation(summary = "Retourne les villes d'un département filtrées par population")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des villes filtrées",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée", content = @Content())
    })
    ResponseEntity<List<VilleDTO>> getVillesByPopulation(
            @PathVariable Long id,
            @Parameter(description = "Population minimale", required = true, example = "1000") @RequestParam int min,
            @Parameter(description = "Population maximale", required = true, example = "100000") @RequestParam int max);
}
