package com.TaskManagement.Task.controllers;

import com.TaskManagement.Task.dto.AllUsersDto;
import com.TaskManagement.Task.dto.AuthResponseDTO;
import com.TaskManagement.Task.dto.LoginDto;
import com.TaskManagement.Task.dto.RegisterDto;
import com.TaskManagement.Task.models.*;
import com.TaskManagement.Task.repository.*;
import com.TaskManagement.Task.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private  AuthenticationManager authenticationManager;
    private  UserRepository userRepository;
    private  RoleRepository roleRepository;
    private  TachesRepesotery tacheRepository;
    private  EquipeRepository equipeRepository;
    private  PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator,
                          TachesRepesotery tacheRepository, EquipeRepository equipeRepository, BlacklistedTokenRepository blacklistedTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.tacheRepository = tacheRepository;
        this.equipeRepository = equipeRepository;
        this.blacklistedTokenRepository = blacklistedTokenRepository;
    }
    public AuthController() {
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken
                        (loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        UserEntity user = userRepository.findByEmail(loginDto.getEmail()).get();
        user.setOnline(true);
        userRepository.save(user);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }



    @PostMapping("/register/**")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("username is taken", HttpStatus.BAD_REQUEST);//knou mawjoud ya3ti bad request
        }
        UserEntity user = new UserEntity();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword())); // decripter le password
        user.setNom(registerDto.getNom());
        user.setPrenom(registerDto.getPrenom());
        user.setSociete(registerDto.getSociete());
        //Roles
        List<UserRole> userRoles = new ArrayList<>();
        for (String role : registerDto.getRoles()) {
            UserRole userRole = roleRepository.findByName(role)
                    .orElseGet(() -> {
                        UserRole newRole = new UserRole();
                        newRole.setName(role);
                        return roleRepository.save(newRole);
                    });
            userRoles.add(userRole);
        }
        user.setRoles(userRoles);
        //taches
        List<tache> userTaches = new ArrayList<>();
        for (String tachee : registerDto.getTaches()) {
            tache usertache = tacheRepository.findByNomTache(tachee)
                    .orElseGet(() -> {
                        tache newTache = new tache();
                        newTache.setNomTache(tachee);
                        return tacheRepository.save(newTache);
                    });
            userTaches.add(usertache);
        }
        user.setTaches(userTaches);

        userRepository.save(user);
        //equipes
        List<Equipe> userEquipes = new ArrayList<>();
        for (String equipe : registerDto.getTaches()) {
            Equipe userequipe = equipeRepository.findByNom(equipe)
                    .orElseGet(() -> {
                        Equipe newEquipe = new Equipe();
                        newEquipe.setNom(equipe);
                        return equipeRepository.save(newEquipe);
                    });
            userEquipes.add(userequipe);
        }
        user.setRoles(userRoles);

        userRepository.save(user);
        return new ResponseEntity<>("user register sucess", HttpStatus.OK);
    }




@PostMapping("/logout")
    public boolean logoutUser(@RequestBody AllUsersDto allUsersDto) {
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);
        BlacklistedToken blacklistedToken = new BlacklistedToken();
        blacklistedToken.setToken(allUsersDto.getAccessToken());
        blacklistedToken.setExpiryDate(expiryDate);
        blacklistedTokenRepository.save(blacklistedToken);
        String email = jwtGenerator.extractEmailFromJwt(allUsersDto.getAccessToken());
       UserEntity user = userRepository.findByEmail(email).get();
       user.setOnline(false);
       userRepository.save(user);
    return true;
    }


}









