package com.TaskManagement.Task.service;

import com.TaskManagement.Task.models.EtatTache;
import com.TaskManagement.Task.models.UserEntity;
import com.TaskManagement.Task.models.tache;
import com.TaskManagement.Task.repository.ProjetRepository;
import com.TaskManagement.Task.repository.TachesRepesotery;
import com.TaskManagement.Task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutoTaskSchedulerServiceImpl implements AutoTaskSchedulerService{



private  final ProjetRepository projetRepository;
private final TachesRepesotery tacheRepository;
    private final UserRepository userRepository;
    @Autowired
    public AutoTaskSchedulerServiceImpl( UserRepository userRepository, ProjetRepository projetRepository,TachesRepesotery tacheRepository) {

        this.userRepository = userRepository;
        this.projetRepository = projetRepository;
        this.tacheRepository = tacheRepository;
    }

   @Override
    public List<tache> scheduleTasks(String societe){
        return  projetRepository.findAll().stream().filter(p->p.getSociete().equals(societe))
                .flatMap(projet -> projet.getTaches().stream())
                .filter(tache -> tache.getEtat().equals(EtatTache.Not_Affected))
                .sorted(Comparator.comparing(tache::getPriorite).thenComparing(tache::getDateFin))
                .collect(Collectors.toList());


    }


    @Override
    public List<UserEntity> assignTaskToEligibleUser(String societe) {
        List<UserEntity> eligibleUsers = userRepository.findByRoleName("employee").stream().filter(p->p.getSociete().equals(societe))
                .filter(user -> user.getTaches().size() < 5)
                .sorted(Comparator.comparingInt(user -> user.getTaches().size()))
                .toList();

        if (eligibleUsers.isEmpty()) {
            eligibleUsers = userRepository.findAll().stream().filter(p->p.getSociete().equals(societe))
                    .sorted(Comparator.comparingInt(user -> user.getTaches().size()))
                    .toList();
        }

        return eligibleUsers;
    }


    public void autoAssignTasks(List<tache> unassignedTasks ,List<UserEntity> eligibleUsers ,String state) {

        if ("on".equalsIgnoreCase(state)) {
            int userIndex = 0;
            for (tache tache : unassignedTasks) {
                UserEntity user = eligibleUsers.get(userIndex);
                user.getTaches().add(tache);
                tache.getUsers().add(user);
                tache.setEtat(EtatTache.EN_ATTENTE);
                userRepository.save(user);
                tacheRepository.save(tache);


                userIndex = (userIndex + 1) % eligibleUsers.size();

            }

        }
    }



}



