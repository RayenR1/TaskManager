package com.TaskManagement.Task.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;//username

    private  String password;

    private String societe;
    private String nom;
    private String prenom;
    //many to many avec role
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="user_roles",joinColumns =@JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    @JsonIgnoreProperties("users")
    private List<UserRole> roles = new ArrayList<>();

    //many to many avec tache
    @ManyToMany(mappedBy = "users")
    // @JsonIgnore
    private List<tache> taches = new ArrayList<>(); /* @JsonBackReference(value = "tache-user")*/
    //many to many avec equipe
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="user_equipes",joinColumns =@JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "equipe_id",referencedColumnName = "id")
    )
    @JsonIgnoreProperties("users")
    private List<Equipe> equipes = new ArrayList<>();

    @Column(name = "online")
    private boolean online=false;

}
