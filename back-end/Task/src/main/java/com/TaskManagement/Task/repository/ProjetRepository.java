package com.TaskManagement.Task.repository;

import com.TaskManagement.Task.models.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProjetRepository extends JpaRepository<Projet,Integer> {
    Optional<Projet> findById(long id);
    @Query("SELECT p FROM Projet p WHERE p.id = :id AND p.societe = :societe")
    Optional<Projet> findByIdAndSociete(Integer id, String societe);
}
