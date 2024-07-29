package com.TaskManagement.Task.repository;

import com.TaskManagement.Task.models.EtatTache;
import com.TaskManagement.Task.models.tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TachesRepesotery extends JpaRepository<tache,Integer> {
Optional<tache> findByNomTache(String nom);
    List<tache> findAllByEtat(EtatTache etat);
    @Query("SELECT t FROM tache t JOIN t.users u WHERE u.id = :userId")
    List<tache> findTachesByUserId(@Param("userId") Long userId);
    @Query("SELECT t FROM tache t JOIN t.users u WHERE u.id = :userId AND t.etat = com.TaskManagement.Task.models.EtatTache.Valide")
    List<tache> findValidatedTachesByUserId(@Param("userId") Long userId);
}
