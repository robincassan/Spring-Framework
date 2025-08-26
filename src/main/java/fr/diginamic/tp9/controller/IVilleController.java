package fr.diginamic.tp9.controller;

import fr.diginamic.tp9.dto.VilleDTO;
import fr.diginamic.tp9.model.Ville;
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

public interface IVilleController {
    @GetMapping
    @Operation(summary = "Retourne la liste paginée de toutes les villes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des villes",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDTO.class))))
    })
    Page<VilleDTO> getAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    @Operation(summary = "Retourne une ville par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville trouvée",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VilleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ville non trouvée", content = @Content())
    })
    ResponseEntity<VilleDTO> getById(
            @Parameter(description = "ID de la ville", example = "34000", required = true)
            @PathVariable Long id);

    @GetMapping("/search")
    @Operation(summary = "Recherche une ville par son nom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville trouvée",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VilleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ville non trouvée", content = @Content())
    })
    ResponseEntity<VilleDTO> getByNom(
            @Parameter(description = "Nom de la ville", example = "Montpellier", required = true)
            @RequestParam String nom);

    @PostMapping
    @Operation(summary = "Crée une nouvelle ville")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville créée",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VilleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erreur de validation", content = @Content())
    })
    ResponseEntity<VilleDTO> create(@RequestBody Ville ville);

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour une ville existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville mise à jour",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VilleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ville introuvable", content = @Content())
    })
    ResponseEntity<VilleDTO> update(@PathVariable Long id, @RequestBody Ville ville);

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime une ville")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ville supprimée"),
            @ApiResponse(responseCode = "404", description = "Ville introuvable", content = @Content())
    })
    ResponseEntity<Void> delete(@PathVariable Long id);

    @GetMapping("/departement/{departementId}")
    @Operation(summary = "Retourne toutes les villes d’un département")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des villes",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée", content = @Content())
    })
    ResponseEntity<List<VilleDTO>> getVillesByDepartement(
            @Parameter(description = "ID du département", example = "34", required = true)
            @PathVariable Long departementId);

    @GetMapping("/departement/{departementId}/filter")
    @Operation(summary = "Retourne les villes d’un département filtrées par population")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des villes filtrées",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée", content = @Content())
    })
    ResponseEntity<List<VilleDTO>> getVillesByDepartementAndPopulation(
            @Parameter(description = "ID du département", example = "34", required = true)
            @PathVariable Long departementId,
            @Parameter(description = "Population minimale", example = "1000", required = true) @RequestParam int min,
            @Parameter(description = "Population maximale", example = "100000", required = true) @RequestParam int max);

    @GetMapping("/search-prefix")
    @Operation(summary = "Retourne les villes dont le nom commence par un préfixe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des villes correspondant au préfixe",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée", content = @Content())
    })
    List<VilleDTO> getVillesByPrefix(
            @Parameter(description = "Préfixe du nom de la ville", example = "Mont", required = true)
            @RequestParam String prefix);

    @GetMapping("/search-pop-min")
    @Operation(summary = "Retourne les villes avec population supérieure ou égale à un minimum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des villes",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée", content = @Content())
    })
    List<VilleDTO> getVillesByPopulationMin(
            @Parameter(description = "Population minimale", example = "5000", required = true) @RequestParam int min);

    @GetMapping("/search-pop-between")
    @Operation(summary = "Retourne les villes avec population comprise entre min et max")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des villes",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VilleDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée", content = @Content())
    })
    List<VilleDTO> getVillesByPopulationBetween(@Parameter(description = "Population minimale", example = "1000", required = true)
                                                @RequestParam int min,
                                                @Parameter(description = "Population maximale", example = "50000", required = true)
                                                @RequestParam int max);
}
