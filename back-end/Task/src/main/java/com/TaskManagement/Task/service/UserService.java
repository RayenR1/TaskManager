package com.TaskManagement.Task.service;

import com.TaskManagement.Task.dto.CreateUserDto;
import com.TaskManagement.Task.dto.RegisterDto;
import com.TaskManagement.Task.dto.UserPerformanceDto;
import com.TaskManagement.Task.models.*;
import com.TaskManagement.Task.repository.EquipeRepository;
import com.TaskManagement.Task.repository.RoleRepository;
import com.TaskManagement.Task.repository.TachesRepesotery;
import com.TaskManagement.Task.repository.UserRepository;
import com.TaskManagement.Task.security.JWTGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TachesRepesotery tacheRepository;
    private final EquipeRepository equipeRepository;
    private final PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       TachesRepesotery tacheRepository, EquipeRepository equipeRepository,
                       PasswordEncoder passwordEncoder,JWTGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tacheRepository = tacheRepository;
        this.equipeRepository = equipeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUser(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public List<UserEntity> getEmployees() {
        List<UserEntity> users = userRepository.findAllByRoleName("employee");
        return users;
    }

    public String registerUser(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            return "username is taken";
        }
        String societe = jwtGenerator.extractSocieteFromJwt(createUserDto.getAccessToken());

        UserEntity user = new UserEntity();
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setNom(createUserDto.getNom());
        user.setPrenom(createUserDto.getPrenom());
        user.setSociete(societe);
        // Roles
        List<UserRole> userRoles = new ArrayList<>();
        for (String role : createUserDto.getRoles()) {
            UserRole userRole = roleRepository.findByName(role)
                    .orElseGet(() -> {
                        UserRole newRole = new UserRole();
                        newRole.setName(role);
                        return roleRepository.save(newRole);
                    });
            userRoles.add(userRole);
        }
        user.setRoles(userRoles);
        // Taches
        List<tache> userTaches = new ArrayList<>();
        for (String tachee : createUserDto.getTaches()) {
            tache usertache = tacheRepository.findByNomTache(tachee)
                    .orElseGet(() -> {
                        tache newTache = new tache();
                        newTache.setNomTache(tachee);
                        return tacheRepository.save(newTache);
                    });
            userTaches.add(usertache);
        }
        user.setTaches(userTaches);
        // Equipes
        List<Equipe> userEquipes = new ArrayList<>();
        for (String equipe : createUserDto.getEquipes()) {
            Equipe userequipe = equipeRepository.findByNom(equipe)
                    .orElseGet(() -> {
                        Equipe newEquipe = new Equipe();
                        newEquipe.setNom(equipe);
                        return equipeRepository.save(newEquipe);
                    });
            userEquipes.add(userequipe);
        }
        user.setEquipes(userEquipes);

        userRepository.save(user);
        return "user register success";
    }

    public String updateUser(Long id, UserEntity user) {
        Optional<UserEntity> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            UserEntity updatedUser = existingUser.get();
            updatedUser.setEmail(user.getEmail());
            updatedUser.setNom(user.getNom());
            updatedUser.setPrenom(user.getPrenom());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setSociete(user.getSociete());
            updatedUser.setRoles(user.getRoles());
             userRepository.save(updatedUser);
            return "user updated successfully";
        } else {
            return "user not found";
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            for (UserRole role : user.getRoles()) {
                role.getUsers().remove(user);
                roleRepository.save(role);
            }
            userRepository.deleteById(id);
        }
    }

    public int CountEmployeeBySocietee(String  societe) {
        List<UserEntity> allUsers = getAllUsers();
        long count = allUsers.stream()
                .filter(user ->  societe.equals(user.getSociete()))
                .count();

        return (int) count;
    }

public int CountByRole(String roleName, String  societe) {
        List<UserEntity> allUsers = getAllUsers();
        long count = allUsers.stream()
                .filter(user ->  societe.equals(user.getSociete()))
                .filter(user -> user.getRoles().stream().anyMatch(role -> roleName.equals(role.getName())))
                .count();

        return (int) count;
    }

    public int countTeamsByCompany(String  societe) {
        List<UserEntity> allUsers = getAllUsers();
        Set<Equipe> uniqueTeams = allUsers.stream()
                .filter(user ->  societe.equals(user.getSociete()))
                .flatMap(user -> user.getEquipes().stream())
                .collect(Collectors.toSet());
        return uniqueTeams.size();
    }


    public long countOnlineUsersBySociete(String societe) {
        return userRepository.findAllBySocieteAndOnline(societe, true).stream().count();
    }

    public long countOnlineAdminUsers(String societe) {
        return userRepository.findAllBySocieteAndOnline(societe, true).stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> "admin".equals(role.getName())))
                .count();
    }

    public List<UserPerformanceDto> getUsersPerformance() {
        return userRepository.findAll().stream().map(user -> {
            long validatedTasksCount = tacheRepository.findAll().stream()
                    .filter(tache -> tache.getEtat().equals(EtatTache.Valide) && tache.getUsers().contains(user))
                    .count();
            UserPerformanceDto dto = new UserPerformanceDto();
            dto.setUserId(user.getId());
            dto.setUserName(user.getNom());
            dto.setValidatedTasksCount((int) validatedTasksCount);
            return dto;
        }).collect(Collectors.toList());
    }

    //tri double sens
    public List<UserEntity> findAllUsersSorted(String sortDirection, String sortBy) {
        Sort sort = "asc".equalsIgnoreCase(sortDirection) ?
                Sort.by(Sort.Direction.ASC, sortBy) :
                Sort.by(Sort.Direction.DESC, sortBy);
        return userRepository.findAll(sort);
    }

}
