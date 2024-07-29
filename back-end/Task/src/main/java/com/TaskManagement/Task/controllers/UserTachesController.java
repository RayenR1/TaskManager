package com.TaskManagement.Task.controllers;


import com.TaskManagement.Task.dto.TacheDto;
import com.TaskManagement.Task.models.tache;
import com.TaskManagement.Task.security.JWTGenerator;
import com.TaskManagement.Task.service.BlacklistedTokenService;
import com.TaskManagement.Task.service.tacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
public class UserTachesController {
    private final tacheService tacheService;
    private final JWTGenerator jwtGenerator;
    private final BlacklistedTokenService blacklistedTokenService;
    @Autowired
    public UserTachesController(tacheService tacheService, JWTGenerator jwtGenerator,BlacklistedTokenService blacklistedTokenService){
        this.tacheService = tacheService;
        this.jwtGenerator = jwtGenerator;
        this.blacklistedTokenService = blacklistedTokenService;
    }


    @GetMapping("/user_taches")
    public ResponseEntity<List<TacheDto>> findTachesByUserId(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        int  userId= jwtGenerator.extractIdFromJwt(token);
        if (roles.contains("employee") && blacklistedTokenService.isTokenBlacklisted(token) == false) {

            List<tache> taches=tacheService.findTachesByUserId((long)userId);
            List<TacheDto> tacheDtos =taches.stream()
                    .map(tacheService::convertToTacheDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(tacheDtos);
        }
        else {
            return ResponseEntity.status(403).build();
        }
    }


    @PutMapping("/changeEtat/t-{tacheId}/newState-{newState}")
    public ResponseEntity<String> changeTacheState(@PathVariable int tacheId, @PathVariable String newState,@RequestHeader("Authorization") String token) {
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("employee") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
        return ResponseEntity.ok(tacheService.changeTacheState(tacheId, newState, societe));}
        else {
            return ResponseEntity.status(403).build();
        }
    }


    @GetMapping("/TaskTovalidate")
    public ResponseEntity<List<TacheDto>> tachesAValide(@RequestHeader("Authorization") String token) {
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && !blacklistedTokenService.isTokenBlacklisted(token)) {
            List<tache> tasksToValidate = tacheService.tachesAValide(societe);
            List<TacheDto> tacheDtos = tasksToValidate.stream()
                    .map(tacheService::convertToTacheDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(tacheDtos);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/changeStatus/t-{taskId}/newStatus-{newStatus}")
    public ResponseEntity<String> changeTaskStatus(@PathVariable int taskId, @PathVariable String newStatus,@RequestHeader("Authorization") String token) {
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
        return ResponseEntity.ok(tacheService.changeTaskStatus(taskId,newStatus,  societe));}
        else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/removeValidated")
    public ResponseEntity<String> removeAndStoreValidatedTask(@RequestHeader("Authorization") String token) {
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            return ResponseEntity.ok(tacheService.removeAndStoreValidatedTasks(societe));
        }
        else {
            return ResponseEntity.status(403).build();
        }


    }

    @GetMapping("/historique")
    public ResponseEntity<List<TacheDto>> historique(@RequestHeader("Authorization") String token) {
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        int userId = jwtGenerator.extractIdFromJwt(token);
        if (roles.contains("employee") && !blacklistedTokenService.isTokenBlacklisted(token)) {
            List<tache> validatedTaches = tacheService.findValidatedTachesForUser((long) userId);
            List<TacheDto> tacheDtos = validatedTaches.stream()
                    .map(tacheService::convertToTacheDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(tacheDtos);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/historique-{userId}")
    public ResponseEntity<List<TacheDto>> historiquee(@PathVariable int userId,@RequestHeader("Authorization") String token) {
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        //int userId = jwtGenerator.extractIdFromJwt(token);
        if (roles.contains("employee") && !blacklistedTokenService.isTokenBlacklisted(token)) {
            List<tache> validatedTaches = tacheService.findValidatedTachesForUser((long) userId);
            List<TacheDto> tacheDtos = validatedTaches.stream()
                    .map(tacheService::convertToTacheDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(tacheDtos);
        } else {
            return ResponseEntity.status(403).build();
        }
    }





}
