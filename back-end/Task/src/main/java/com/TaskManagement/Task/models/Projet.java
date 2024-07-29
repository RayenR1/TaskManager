package com.TaskManagement.Task.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="projets")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nomProjet;
    private String description;
    private String societe;
    private int nbrTaches=0;
    private int nbrTachesTerminees=0;
    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "projet-tache")
    private List<tache> taches;


}