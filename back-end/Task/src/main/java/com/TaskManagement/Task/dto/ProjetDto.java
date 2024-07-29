package com.TaskManagement.Task.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjetDto {
    private int id;
    private String nomProjet;
    private String description;
    private String societe;
    private int nbrTaches;
    private int nbrTachesTerminees;
    private List<TacheDto> taches;
}
