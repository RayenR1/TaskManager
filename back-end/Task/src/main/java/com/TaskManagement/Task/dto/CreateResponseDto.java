package com.TaskManagement.Task.dto;

import lombok.Data;

@Data
public class CreateResponseDto {
    private String response;
    private String result;
    public CreateResponseDto(String response,String result){
        this.response=response;
        this.result=result;
    }
}
