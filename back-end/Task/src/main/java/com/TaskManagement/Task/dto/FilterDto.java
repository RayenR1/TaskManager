package com.TaskManagement.Task.dto;

import lombok.Data;

@Data
public class FilterDto {
    private String accessToken;
    private String fieldName;
    private String value;
    private String operation;
}
