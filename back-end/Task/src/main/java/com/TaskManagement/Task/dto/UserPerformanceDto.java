package com.TaskManagement.Task.dto;

import lombok.Data;

@Data
public class UserPerformanceDto {
    private int userId;
    private String userName;
    private int validatedTasksCount;
}
