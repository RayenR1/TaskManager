package com.TaskManagement.Task.dto;

import lombok.Data;

import java.util.List;
@Data
public class TacheDto {
    private int id;
    private String nomTache;
    private String description;
    private String dateDebut;
    private String dateFin;
    private String etat;
    private String priorite;
    private List<Integer> users;
}
