package com.TaskManagement.Task.dto;

import lombok.Data;

import java.util.List;
@Data
public class CreateUserDto extends RegisterDto {
    private String accessToken;
    private String passwordAdmin;
}
