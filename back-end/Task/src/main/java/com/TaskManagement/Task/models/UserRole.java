package com.TaskManagement.Task.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name="roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  String name;
    @ManyToMany(mappedBy = "roles")
   private List<UserEntity> users=new ArrayList<>();

}
