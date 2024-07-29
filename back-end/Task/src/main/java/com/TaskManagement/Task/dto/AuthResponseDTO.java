package com.TaskManagement.Task.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private  String accessToken;
    private  String tokenType="Bearer ";
    private boolean result=true;
    public  AuthResponseDTO(String accessToken){
        this.accessToken=accessToken;
    }
}
