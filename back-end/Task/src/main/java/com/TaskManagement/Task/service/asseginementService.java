package com.TaskManagement.Task.service;

import com.TaskManagement.Task.models.EtatTache;
import com.TaskManagement.Task.models.UserEntity;
import com.TaskManagement.Task.models.tache;
import com.TaskManagement.Task.repository.ProjetRepository;
import com.TaskManagement.Task.repository.TachesRepesotery;
import com.TaskManagement.Task.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class asseginementService {
private final UserRepository userRepository;
private final TachesRepesotery tacheRepository;
private  final ProjetRepository projetRepository;
private final ProjetService projetService;

@Autowired
    public asseginementService(UserRepository userRepository, TachesRepesotery tacheRepository, ProjetRepository projetRepository, ProjetService projetService) {
        this.userRepository = userRepository;
        this.tacheRepository = tacheRepository;
        this.projetRepository = projetRepository;
        this.projetService = projetService;
    }

    public boolean assignTaskToUser(int ProjectId,int taskId, Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        tache tache=projetService.trouverTacheDansProjetParId(ProjectId,taskId);

        if (userOptional.isPresent() && tache!=null )
            if (userOptional.isPresent() && tache != null) {
                UserEntity user = userOptional.get();
                boolean hasEmployeeRole = user.getRoles().stream()
                        .anyMatch(role -> role.getName().equalsIgnoreCase("employee"));
                if (hasEmployeeRole) {
            tache.getUsers().add(userOptional.get());
            userOptional.get().getTaches().add(tache);
            tache.setEtat(EtatTache.EN_ATTENTE);
            userRepository.save(userOptional.get());
            tacheRepository.save(tache);
            return true;}
        }
        return false;
    }


    public boolean removeTaskFromUser(int ProjectId,int taskId, Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        tache tache = projetService.trouverTacheDansProjetParId(ProjectId, taskId);

        if (userOptional.isPresent() && tache != null ) {
            if (userOptional.isPresent() && tache != null) {
                UserEntity user = userOptional.get();
                boolean hasEmployeeRole = user.getRoles().stream()
                        .anyMatch(role -> role.getName().equalsIgnoreCase("employee"));
                if (hasEmployeeRole) {
                    tache.getUsers().remove(userOptional.get());
                    userOptional.get().getTaches().remove(tache);
                    tache.setEtat(EtatTache.Not_Affected);
                    userRepository.save(userOptional.get());
                    tacheRepository.save(tache);
                    return true;
                }
            }

        }
        return false;
    }

}
