package com.TaskManagement.Task.service;

import com.TaskManagement.Task.dto.ProjetDto;
import com.TaskManagement.Task.dto.TacheDto;
import com.TaskManagement.Task.models.EtatTache;

import com.TaskManagement.Task.models.Projet;
import com.TaskManagement.Task.models.UserEntity;
import com.TaskManagement.Task.models.tache;
import com.TaskManagement.Task.repository.ProjetRepository;
import com.TaskManagement.Task.repository.TachesRepesotery;
import com.TaskManagement.Task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class tacheService {
    private final UserRepository userRepository;
    private final TachesRepesotery tacheRepository;
    private  final ProjetRepository projetRepository;
    private final ProjetService projetService;

    @Autowired
    public tacheService(UserRepository userRepository,TachesRepesotery tacheRepository,ProjetRepository projetRepository,ProjetService projetService) {
        this.userRepository = userRepository;
        this.tacheRepository = tacheRepository;
        this.projetRepository = projetRepository;
        this.projetService = projetService;
    }

    public List<tache> findTachesByUserId(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            return user.getTaches();
        }
        return Collections.emptyList();
    }


    public String changeTacheState(int tacheId, String newState, String societe) {
        Optional<tache> tacheOptional = projetRepository.findAll().stream()
                .filter(p -> p.getSociete().equals(societe))
                .flatMap(projet -> projet.getTaches().stream())
                .filter(tache -> tache.getId() == tacheId)
                .findFirst();
        if (tacheOptional.isPresent()) {
            tache tache = tacheOptional.get();
            switch(newState) {
                    case"EN_ATTENTE"-> tache.setEtat(EtatTache.EN_ATTENTE);
                    case"EN_COURS"-> tache.setEtat(EtatTache.EN_COURS);
                    case"TERMINEE"-> tache.setEtat(EtatTache.TERMINEE);
            }
            tacheRepository.save(tache);
            return "Task state updated successfully";
        } else {
            return "Task not found";
        }
    }

    public List<tache> tachesAValide(String societe){
        return  projetRepository.findAll().stream().filter(p->p.getSociete().equals(societe))
                .flatMap(projet -> projet.getTaches().stream())
                .filter(tache -> tache.getEtat().equals(EtatTache.TERMINEE))
                .collect(Collectors.toList());

    }


    public String changeTaskStatus(int taskId, String newStatus, String societe) {
        System.out.println("Changing status for task ID: " + taskId + " to " + newStatus + " in company " + societe);
        Optional<tache> tacheOptional = projetRepository.findAll().stream()
                .filter(p -> p.getSociete().equals(societe))
                .flatMap(projet -> projet.getTaches().stream())
                .filter(tache -> tache.getId() == taskId)
                .findFirst();

        if (tacheOptional.isPresent()) {
            tache task = tacheOptional.get();
            Projet project = task.getProjet();
            if ("Valide".equals(newStatus)) {
                System.out.println("Setting task to Valide");
                task.setEtat(EtatTache.Valide);
                tacheRepository.save(task);
                project.setNbrTachesTerminees(project.getNbrTachesTerminees() + 1);
                projetRepository.save(project);
                this.removeAndStoreValidatedTasks(societe);
            } else {
                System.out.println("Setting task to EN_COURS");
                task.setEtat(EtatTache.EN_COURS);
                tacheRepository.save(task);
            }
            return "Task status updated successfully";
        } else {
            System.out.println("Task not found");
            return "Task not found";
        }
    }

//pmpmpm max verstapen

    public String removeAndStoreValidatedTasks(String societe) {
        System.out.println("Removing and storing validated tasks for company: " + societe);
        boolean taskFoundAndRemoved = false;
        List<tache> taches = projetRepository.findAll().stream()
                .filter(p -> p.getSociete().equals(societe))
                .flatMap(projet -> projet.getTaches().stream())
                .filter(tache -> tache.getEtat().equals(EtatTache.Valide))
                .collect(Collectors.toList());

        System.out.println("Validated tasks found: " + taches.size());

        for (tache tache : taches) {
            System.out.println("Processing task ID: " + tache.getId());
            List<UserEntity> usersToUpdate = new ArrayList<>(tache.getUsers());
            for (UserEntity user : usersToUpdate) {
                System.out.println("Removing task from user ID: " + user.getId());
                user.getTaches().remove(tache);
                userRepository.save(user);
            }
            //tache.getUsers().clear();
            tacheRepository.save(tache);
            taskFoundAndRemoved = true;
        }

        if (taskFoundAndRemoved) {
            return "Tasks removed from users' lists and stored successfully";
        } else {
            return "No tasks found or do not meet criteria for removal";
        }
    }
/// historique du taches

    public List<tache> findValidatedTachesForUser(Long userId) {
        System.out.println("Fetching validated tasks for user ID: " + userId);

        // Fetch all validated tasks
        List<tache> allValidatedTaches = tacheRepository.findAll().stream()
                .filter(tache -> tache.getEtat().equals(EtatTache.Valide))
                .collect(Collectors.toList());

        System.out.println("Total validated tasks found: " + allValidatedTaches.size());

        // Debugging: Print users associated with each validated task
        for (tache t : allValidatedTaches) {
            System.out.println("Task ID: " + t.getId() + " Users: " + t.getUsers().stream()
                    .map(UserEntity::getId)
                    .collect(Collectors.toList()));
        }

        // Filter validated tasks by user ID
        List<tache> userValidatedTaches = allValidatedTaches.stream()
                .filter(tache -> tache.getUsers().stream().anyMatch(user -> (long)user.getId() == userId))
                .collect(Collectors.toList());

        System.out.println("Validated tasks for user ID " + userId + " found: " + userValidatedTaches.size());

        return userValidatedTaches;
    }

    public ProjetDto convertToProjetDto(Projet projet) {
        ProjetDto dto = new ProjetDto();
        dto.setId(projet.getId());
        dto.setNomProjet(projet.getNomProjet());
        dto.setDescription(projet.getDescription());
        dto.setSociete(projet.getSociete());
        dto.setNbrTaches(projet.getNbrTaches());
        dto.setNbrTachesTerminees(projet.getNbrTachesTerminees());
        dto.setTaches(projet.getTaches().stream().map(this::convertToTacheDto).collect(Collectors.toList()));
        return dto;
    }

    public TacheDto convertToTacheDto(tache tache) {
        TacheDto dto = new TacheDto();
        dto.setId(tache.getId());
        dto.setNomTache(tache.getNomTache());
        dto.setDescription(tache.getDescription());
        dto.setDateDebut(tache.getDateDebut());
        dto.setDateFin(tache.getDateFin());
        dto.setEtat(tache.getEtat().toString());
        dto.setPriorite(tache.getPriorite().toString());
        dto.setUsers(tache.getUsers().stream().map(UserEntity::getId).collect(Collectors.toList()));
        dto.setUsers(tache.getUsers().stream().map(UserEntity::getId).collect(Collectors.toList()));
        return dto;
    }


}



