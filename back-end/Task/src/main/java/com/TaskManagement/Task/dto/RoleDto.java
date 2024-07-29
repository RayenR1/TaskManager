package com.TaskManagement.Task.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoleDto {
    private  String accessToken;
    private String role;
    public RoleDto(String accessToken){
        this.accessToken=accessToken;
    }
    public RoleDto(){}
    @JsonCreator
    public RoleDto(@JsonProperty("accessToken") String accessToken, @JsonProperty("role") String role) {
        this.accessToken = accessToken;
        this.role = role;
    }
}
