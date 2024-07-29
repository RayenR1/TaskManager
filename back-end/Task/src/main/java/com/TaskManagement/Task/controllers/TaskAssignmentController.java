package com.TaskManagement.Task.controllers;

import com.TaskManagement.Task.dto.CreateResponseDto;
import com.TaskManagement.Task.security.JWTGenerator;
import com.TaskManagement.Task.service.AutoTaskSchedulerServiceImpl;
import com.TaskManagement.Task.service.BlacklistedTokenService;
import com.TaskManagement.Task.service.asseginementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskAssignmentController {
    private final asseginementService assignmentService;
    private final JWTGenerator jwtGenerator;
    private final BlacklistedTokenService blacklistedTokenService;
    private final AutoTaskSchedulerServiceImpl autoTaskSchedulerService;
    @Autowired
    public TaskAssignmentController(asseginementService assignmentService, JWTGenerator jwtGenerator, BlacklistedTokenService blacklistedTokenService,
                                    AutoTaskSchedulerServiceImpl autoTaskSchedulerService) {
        this.assignmentService = assignmentService;
        this.jwtGenerator = jwtGenerator;
        this.blacklistedTokenService = blacklistedTokenService;
        this.autoTaskSchedulerService = autoTaskSchedulerService;
    }




    @PostMapping("/p-{projectId}/t-{taskId}/assign-{userId}")
    public ResponseEntity<CreateResponseDto> assignTaskToUser(@PathVariable int projectId, @PathVariable int taskId, @PathVariable Long userId, @RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
        boolean result = assignmentService.assignTaskToUser(projectId, taskId, userId);
        if (result==true) {
            return ResponseEntity.ok(new CreateResponseDto("Task successfully assigned to user.","true"));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("probleme","false") );
        }
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n avez pas le droit de faire cette operation","false") );

        }
    }

    @PostMapping("/p-{projectId}/t-{taskId}/remove-{userId}")
    public ResponseEntity<CreateResponseDto> removeTaskFromUser(@PathVariable int projectId, @PathVariable int taskId, @PathVariable Long userId,@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {

            boolean result = assignmentService.removeTaskFromUser(projectId, taskId, userId);
            if (result) {
                return ResponseEntity.ok(new CreateResponseDto("Task successfully removed from user.","true"));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("Failed to remove task from user.","false") );

            }
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n avez pas le droit de faire cette operation","false") );
        }
    }

   @PostMapping("autoassign-{state}")
    public ResponseEntity<CreateResponseDto> autoassignTaskToUser( @PathVariable String state,@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("director") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            autoTaskSchedulerService.autoAssignTasks(autoTaskSchedulerService.scheduleTasks(societe),autoTaskSchedulerService.assignTaskToEligibleUser(societe),state);

            return ResponseEntity.ok(new CreateResponseDto("Task successfully assigned to user.","true"));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n avez pas le droit de faire cette operation","false") );


    }

}
