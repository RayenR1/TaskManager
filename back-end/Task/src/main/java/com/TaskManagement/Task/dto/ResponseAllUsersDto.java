package com.TaskManagement.Task.dto;

import com.TaskManagement.Task.models.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class ResponseAllUsersDto {
    private List<UserEntity> users;
    private String etat;
    public ResponseAllUsersDto(List<UserEntity> users){
        this.users=users;
    }

    public ResponseAllUsersDto(List<UserEntity> users, String etat) {
        this.users = users;
        this.etat = etat;
    }
    public ResponseAllUsersDto(String etat){ this.etat = etat;}
}
