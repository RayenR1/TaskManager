package com.TaskManagement.Task.service;

import com.TaskManagement.Task.models.UserEntity;
import com.TaskManagement.Task.models.tache;
import java.util.List;
public interface AutoTaskSchedulerService {
    public List<tache> scheduleTasks(String societe);
    public List<UserEntity> assignTaskToEligibleUser(String societe);
}
