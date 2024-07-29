package com.TaskManagement.Task.dto;

import lombok.Data;

@Data
public class AllUsersDto {
    private String accessToken;
    private String password;
    public AllUsersDto(String accessToken){
        this.accessToken=accessToken;
    }
    public AllUsersDto(String accessToken,String password){
        this.accessToken=accessToken;
        this.password=password;
    }
    public AllUsersDto(){}
}
