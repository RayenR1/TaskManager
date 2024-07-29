package com.TaskManagement.Task.repository;

import com.TaskManagement.Task.models.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe,Integer> {

    Optional<Equipe> findByNom(String nom);
}
