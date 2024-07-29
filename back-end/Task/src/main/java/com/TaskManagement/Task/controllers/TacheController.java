package com.TaskManagement.Task.controllers;

import com.TaskManagement.Task.dto.CreateResponseDto;
import com.TaskManagement.Task.models.Projet;
import com.TaskManagement.Task.models.tache;
import com.TaskManagement.Task.security.JWTGenerator;
import com.TaskManagement.Task.service.BlacklistedTokenService;
import com.TaskManagement.Task.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
public class TacheController {
    private final ProjetService projetService;
    private final BlacklistedTokenService blacklistedTokenService;
    private final JWTGenerator jwtGenerator;
    @Autowired
    public TacheController(ProjetService projetService,JWTGenerator jwtGenerator,BlacklistedTokenService blacklistedTokenService){
        this.projetService = projetService;
        this.jwtGenerator = jwtGenerator;
        this.blacklistedTokenService = blacklistedTokenService;
    }

//Affichage
    @GetMapping("/p-{projetId}/alltaches")
    public ResponseEntity<List<tache>> findAllTachesInProjet(@PathVariable int projetId,@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            List<tache> taches = projetService.getTachesByProjetId(projetId);
            return ResponseEntity.ok(taches);}
        else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/p-{projetId}/tache-{tacheId}")
    public ResponseEntity<tache> trouverTacheDansProjetParId(@PathVariable int projetId, @PathVariable int tacheId,@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            tache tache = projetService.trouverTacheDansProjetParId(projetId, tacheId);
            return ResponseEntity.ok(tache);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    //CRUD

    @PostMapping("/p-{projetId}/addTache")
    public ResponseEntity<CreateResponseDto> addTacheAuProjet(@PathVariable int projetId, @RequestBody tache nouvelleTache, @RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
        Projet updatedProjet = projetService.addTacheAuProjet(projetId, nouvelleTache);
        return ResponseEntity.ok(new CreateResponseDto("tache ajouter","true"));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n avez pas le droit de faire cette operation","false") );
        }
    }



    @DeleteMapping("/p-{projetId}/deleteTache-{tacheId}")
    public ResponseEntity<CreateResponseDto> deleteTacheDuProjet(@PathVariable int projetId, @PathVariable Long tacheId,@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
        Projet updatedProjet = projetService.deleteTacheDuProjet(projetId, tacheId);
        return ResponseEntity.ok(new CreateResponseDto("soᴜᴉʇɹǝzǝq","true"));}
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n avez pas le droit de faire cette operation","false") );
        }
    }


    @PutMapping("/p-{projetId}/updateTache-{tacheId}")
    public ResponseEntity<CreateResponseDto> updateTacheDansProjet(@PathVariable int projetId, @PathVariable Long tacheId, @RequestBody tache updatedTache,@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {

            Projet projet = projetService.updateTacheDansProjet(projetId, tacheId, updatedTache);
            return ResponseEntity.ok(new CreateResponseDto("tache modifier","true"));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n avez pas le droit de faire cette operation","false") );
    }














}
