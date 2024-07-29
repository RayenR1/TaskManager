package com.TaskManagement.Task.controllers;

import com.TaskManagement.Task.dto.CalculeResopnseDto;
import com.TaskManagement.Task.dto.CreateResponseDto;
import com.TaskManagement.Task.dto.ProjetDto;

import com.TaskManagement.Task.models.Projet;

import com.TaskManagement.Task.security.JWTGenerator;
import com.TaskManagement.Task.service.BlacklistedTokenService;
import com.TaskManagement.Task.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projets")
public class ProjetController {
    private final JWTGenerator jwtGenerator;
    private final ProjetService projetService;
    private final BlacklistedTokenService blacklistedTokenService;
    @Autowired
    public ProjetController(ProjetService projetService, JWTGenerator jwtGenerator,BlacklistedTokenService blacklistedTokenService) {
        this.jwtGenerator = jwtGenerator;
        this.projetService = projetService;
        this.blacklistedTokenService = blacklistedTokenService;
    }

//Affichage


    @GetMapping("/allProjects")
    public ResponseEntity<List<ProjetDto>> listerTousLesProjets(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && !blacklistedTokenService.isTokenBlacklisted(token)) {


            List<Projet> projets = projetService.listerTousLesProjets().stream()
                    .filter(p -> p.getSociete().equals(societe))
                    .collect(Collectors.toList());
            List<ProjetDto> projetDtos = projets.stream().map(projetService::convertToProjetDto).collect(Collectors.toList());
            return ResponseEntity.ok(projetDtos);
        } else {
            return ResponseEntity.status(403).build();
        }
    }
    @GetMapping("/Project-{id}")
    public ResponseEntity<?> trouverProjetParId(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && !blacklistedTokenService.isTokenBlacklisted(token)) {
            Projet projet = projetService.trouverProjetParId(id, societe);
            if (projet != null) {
                ProjetDto projetDto = projetService.convertToProjetDto(projet);
                return ResponseEntity.ok(projetDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

//CRUD project
    @PostMapping("/add")
    public ResponseEntity<CreateResponseDto> addProjet(@RequestBody Projet projet, @RequestHeader("Authorization") String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            projet.setSociete(societe);
            projetService.addProjet(projet);
            return ResponseEntity.ok(new CreateResponseDto("new project has benn created ","true"));}
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n avez pas le droit de faire cette operation","false") );
        }
    }

    @DeleteMapping("/delete-{id}")
    public ResponseEntity<CreateResponseDto> deleteProjet(@PathVariable int id, @RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {

        projetService.deleteProjet(id);
        return ResponseEntity.ok(new CreateResponseDto("le projet est suprimer","true"));}
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n avez pas le droit de faire cette operation","false") );
        }
    }

    @PutMapping("/update-{id}")
    public ResponseEntity<CreateResponseDto> updateProjet(@PathVariable Long id, @RequestBody Projet projet,@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            String response = projetService.updateProjet(id, projet);
            if (response.contains("succès")) {
                return ResponseEntity.ok(new CreateResponseDto(response,"true"));
            } else {
                return ResponseEntity.ok(new CreateResponseDto(response,"false"));
            }
        } else {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n avez pas le droit de faire cette operation","false") );
        }
    }

///calcule
@GetMapping("/nbrTachestotale")
    public ResponseEntity<CalculeResopnseDto> nbrTaches(@RequestHeader("Authorization") String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            int count = projetService.calculeNbreTacheAll(societe);
            return ResponseEntity.ok(new CalculeResopnseDto((long)count));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CalculeResopnseDto(-1));
        }
    }

    @GetMapping("/p-{id}/nbrTachesTerminees")
    public ResponseEntity<CalculeResopnseDto> nbrTachesTermine( @PathVariable int id,@RequestHeader("Authorization") String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            int count = projetService.calculeNbretachesTerminees(id,societe);
            return ResponseEntity.ok(new CalculeResopnseDto((long)count));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CalculeResopnseDto(-1));
        }
    }

    @GetMapping("/p-{id}/nbrTaches")
    public ResponseEntity<CalculeResopnseDto> nbrTachesProjet( @PathVariable int id,@RequestHeader("Authorization") String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            int count = projetService.trouverProjetParId(id,societe).getNbrTaches();
            return ResponseEntity.ok(new CalculeResopnseDto((long)count));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CalculeResopnseDto(-1));
        }
    }



}
