package com.TaskManagement.Task.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="equipes")
@Data
@NoArgsConstructor

public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="user_equipes",joinColumns =@JoinColumn(name="equipe_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
    )
    @JsonIgnoreProperties("equipes")
    private List<UserEntity> users = new ArrayList<>();
}
