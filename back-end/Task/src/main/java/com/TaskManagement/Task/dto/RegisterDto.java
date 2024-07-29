package com.TaskManagement.Task.dto;

import com.TaskManagement.Task.models.Equipe;
import com.TaskManagement.Task.models.tache;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RegisterDto {
    private  String email;
    private String password;
    private String societe;
    private String nom;
    private String prenom;
    private List<String> roles;
    private List<String> taches ;
    private List<String> equipes;
}
