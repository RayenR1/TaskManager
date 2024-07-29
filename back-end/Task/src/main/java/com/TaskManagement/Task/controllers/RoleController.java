package com.TaskManagement.Task.controllers;



import com.TaskManagement.Task.dto.RoleDto;
import com.TaskManagement.Task.security.JWTGenerator;
import com.TaskManagement.Task.service.BlacklistedTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private JWTGenerator jwtGenerator;
    private final BlacklistedTokenService blacklistedTokenService;

    @Autowired
    public RoleController(JWTGenerator jwtGenerator,BlacklistedTokenService blacklistedTokenService) {
        this.jwtGenerator = jwtGenerator;
        this.blacklistedTokenService = blacklistedTokenService;
    }

    @PostMapping("/check")
    public ResponseEntity<RoleDto> checkRole(@RequestBody RoleDto roleCheckDto) {
        // Extract the token from the accessToken property of the JSON
        String token = roleCheckDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
       boolean etatToken= blacklistedTokenService.isTokenBlacklisted(token);
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);

        if (roles.contains(roleCheckDto.getRole())&& etatToken==false) {
            return ResponseEntity.ok(new RoleDto(token,"true"));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RoleDto("vous n'avez pas le droit d'acceder a cette ressource","false"));
        }
    }

    @GetMapping("/check2")
    public ResponseEntity<RoleDto> checkRole2(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        boolean etatToken= blacklistedTokenService.isTokenBlacklisted(token);
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String result="";
        for(String role:roles){
            result=result+"|"+role;
        }

        return ResponseEntity.ok(new RoleDto(token,result));
    }
}
