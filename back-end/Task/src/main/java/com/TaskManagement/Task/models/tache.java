package com.TaskManagement.Task.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name="taches")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nomTache;
    private String type;
    private String description;
    private String dateDebut;
    private String dateFin;
    private EtatTache etat=EtatTache.Not_Affected;//enum
    private Priorite priorite;//enum
   //private String status="non_valide";
    //many to many avec user
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="user_taches",joinColumns =@JoinColumn(name="tache_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
    )
    private List<UserEntity> users = new ArrayList<>(); /* @JsonManagedReference(value = "tache-user")*/
    //many to one avec projet
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id")
    @JsonBackReference(value = "projet-tache")
    private Projet projet;

}
