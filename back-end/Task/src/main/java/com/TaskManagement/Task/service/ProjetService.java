package com.TaskManagement.Task.service;

import com.TaskManagement.Task.dto.ProjetDto;
import com.TaskManagement.Task.dto.TacheDto;
import com.TaskManagement.Task.models.EtatTache;
import com.TaskManagement.Task.models.Projet;
import com.TaskManagement.Task.models.UserEntity;
import com.TaskManagement.Task.models.tache;
import com.TaskManagement.Task.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetService {
    private final ProjetRepository projetRepository;

    @Autowired
    public ProjetService(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }


    public List<Projet> listerTousLesProjets() {
        return projetRepository.findAll();
    }

    public Projet trouverProjetParId(int id, String societe) {
        Optional<Projet> projet = projetRepository.findByIdAndSociete(id, societe);
        return projet.get();

    }

    public Projet addProjet(Projet projet) {
        return projetRepository.save(projet);
    }

    public void deleteProjet(int id) {

        projetRepository.deleteById(id);
    }

    public String updateProjet(Long id, Projet projet) {
        Optional<Projet> projetOptional = projetRepository.findById(id);
        if (projetOptional.isPresent()) {
            Projet projet1 = projetOptional.get();
            projet1.setNomProjet(projet.getNomProjet());
            projet1.setDescription(projet.getDescription());
            projetRepository.save(projet1);
            return "projet modifié avec succès";
        } else {
            return "Projet non trouvé pour l'id ";
        }
    }

    ///crude taches dans projet
    public Projet addTacheAuProjet(int projetId, tache nouvelleTache) {
        Projet projet = projetRepository.findById((long) projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé pour l'id " + projetId));
        nouvelleTache.setProjet(projet);
        nouvelleTache.setEtat(EtatTache.Not_Affected);
        projet.setNbrTaches(projet.getNbrTaches()+1);
        projet.getTaches().add(nouvelleTache);
        return projetRepository.save(projet);
    }
    public Projet deleteTacheDuProjet(int projetId, Long tacheId) {
        Projet projet = projetRepository.findById((long) projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé pour l'id " + projetId));
        projet.getTaches().removeIf(tache -> tache.getId()==tacheId);
        projet.setNbrTaches(projet.getNbrTaches()-1);
        return projetRepository.save(projet);
    }
    public Projet updateTacheDansProjet(int projetId, Long tacheId, tache nouvelleTache) {
        Projet projet = projetRepository.findById((long) projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé pour l'id " + projetId));
        projet.getTaches().forEach(tache -> {
            if (tache.getId()==tacheId) {
                tache.setNomTache(nouvelleTache.getNomTache());
                tache.setType(nouvelleTache.getType());
                tache.setDescription(nouvelleTache.getDescription());
                tache.setDateDebut(nouvelleTache.getDateDebut());
                tache.setDateFin(nouvelleTache.getDateFin());
                tache.setEtat(nouvelleTache.getEtat());
                tache.setPriorite(nouvelleTache.getPriorite());
            }
        });
        return projetRepository.save(projet);
    }
    public List<tache> getTachesByProjetId(int projetId) {
        Projet projet = projetRepository.findById((long) projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé pour l'id " + projetId));
        return projet.getTaches();
    }
    public tache trouverTacheDansProjetParId(int projetId, int tacheId) {
        Projet projet = projetRepository.findById((long) projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé pour l'id " + projetId));
        return projet.getTaches().stream()
                .filter(tache -> tache.getId()==tacheId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tache non trouvée pour l'id " + tacheId));
    }

    //

    public int calculeNbretachesTerminees(int projetId,String societe) {
        Projet projet = projetRepository.findByIdAndSociete( projetId,societe)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé pour l'id " + projetId));
        projetRepository.save(projet);
        return (int) projet.getTaches().stream().filter(tache -> tache.getEtat().equals(EtatTache.Valide)).count();
    }
public int calculeNbreTacheAll(String societe){
        List<Projet> projects=listerTousLesProjets();
        int count=0;
        for (Projet projet:projects){
            if (projet.getSociete().equals(societe)){
                count+=projet.getTaches().size();
            }
        }
        return count;
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
        return dto;
    }

}
