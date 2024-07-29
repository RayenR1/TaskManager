package com.TaskManagement.Task.controllers;

import com.TaskManagement.Task.dto.*;
import com.TaskManagement.Task.models.Equipe;
import com.TaskManagement.Task.models.UserEntity;
import com.TaskManagement.Task.models.UserRole;
import com.TaskManagement.Task.models.tache;
import com.TaskManagement.Task.repository.EquipeRepository;
import com.TaskManagement.Task.repository.RoleRepository;
import com.TaskManagement.Task.repository.TachesRepesotery;
import com.TaskManagement.Task.repository.UserRepository;
import com.TaskManagement.Task.service.BlacklistedTokenService;
import com.TaskManagement.Task.service.FiltersService;
import com.TaskManagement.Task.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.TaskManagement.Task.security.JWTGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    ////INJECTION
    private final UserService userService;
    private final JWTGenerator jwtGenerator;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;
    private final TachesRepesotery tacheRepository;
    private final EquipeRepository equipeRepository;
    private final BlacklistedTokenService blacklistedTokenService;
    private final FiltersService filtersService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, JWTGenerator jwtGenerator, PasswordEncoder passwordEncoder,
                          UserRepository userRepository, RoleRepository roleRepository, TachesRepesotery tacheRepository
            , EquipeRepository equipeRepository, BlacklistedTokenService blacklistedTokenService, FiltersService filtersService) {
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.tacheRepository = tacheRepository;
        this.equipeRepository = equipeRepository;
        this.blacklistedTokenService = blacklistedTokenService;
        this.filtersService = filtersService;
        this.userRepository = userRepository;
    }


    ////CRUD
    @PostMapping("/show_users")
    public ResponseEntity<ResponseAllUsersDto> getAllUsers(@RequestBody AllUsersDto allUsersDto) {
        String token = allUsersDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("admin") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            List<UserEntity> users = userService.getAllUsers().stream().filter(user -> user.getSociete().equals(societe)).collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseAllUsersDto(users, "mnadem"));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseAllUsersDto("Vous n'avez pas le droit d'accéder à cette ressource"));
        }
    }


    /// get user by id
    @PostMapping("/show_users-{id}")
    public UserEntity getUser(@PathVariable Long id, @RequestBody AllUsersDto allUsersDto) {
        String token = allUsersDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);

        if (roles.contains("admin") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            return userService.getUser(id);
        } else {
            return null;
        }

    }

    @PostMapping("/show_employees")
    public ResponseEntity<ResponseAllUsersDto> getEmployee( @RequestBody AllUsersDto allUsersDto) {
        String token = allUsersDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);

        if (roles.contains("admin") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            List<UserEntity> users=userService.getEmployees();
            return ResponseEntity.ok(new ResponseAllUsersDto(users, "mnadem"));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseAllUsersDto("Vous n'avez pas le droit d'accéder à cette ressource"));
        }

    }

    @PostMapping("/create")
    public ResponseEntity<CreateResponseDto> createUser(@RequestBody CreateUserDto createUserDto) {
        String token = createUserDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);

        if (roles.contains("admin") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            String result = userService.registerUser(createUserDto);
            if (result.equals("user register success")) {
                return ResponseEntity.ok(new CreateResponseDto(result, "true"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateResponseDto(result, "false"));

            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n'avez pas le droit d'acceder a cette ressource", "false"));
        }

    }

    ////modification du password ne fonctionne pas
    @PutMapping("/update-{id}")
    public ResponseEntity<CreateResponseDto> updateUser(@PathVariable Long id, @RequestBody CreateUserDto createUserDto) {
        String token = createUserDto.getAccessToken();
        String pass = createUserDto.getPasswordAdmin();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String password = jwtGenerator.extractPasswordFromJwt(token);
        int idFromToken = jwtGenerator.extractIdFromJwt(token);
        String societeFromToken = jwtGenerator.extractSocieteFromJwt(token);
        UserEntity user1 = userService.getUser(id);
        boolean isAdmin = user1.getRoles().stream().anyMatch(role -> role.getName().equals("admin"));
        if ((roles.contains("admin") && passwordEncoder.matches(pass, password) && !isAdmin && societeFromToken.equals(user1.getSociete()) && blacklistedTokenService.isTokenBlacklisted(token) == false)
                || (roles.contains("admin") && user1.getId() == idFromToken && blacklistedTokenService.isTokenBlacklisted(token) == false)) {
            UserEntity user = new UserEntity();
            user.setEmail(createUserDto.getEmail());
            user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
            // user.setPassword(createUserDto.getPassword());
            user.setNom(createUserDto.getNom());
            user.setPrenom(createUserDto.getPrenom());
            user.setSociete(societeFromToken);
            //Roles
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
            //taches
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


            //equipes
            List<Equipe> userEquipes = new ArrayList<>();
            for (String equipe : createUserDto.getTaches()) {
                Equipe userequipe = equipeRepository.findByNom(equipe)
                        .orElseGet(() -> {
                            Equipe newEquipe = new Equipe();
                            newEquipe.setNom(equipe);
                            return equipeRepository.save(newEquipe);
                        });
                userEquipes.add(userequipe);
            }
            user.setRoles(userRoles);


            userService.updateUser(id, user);
            return ResponseEntity.ok(new CreateResponseDto("user updated", "true"));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n'avez pas le droit de modifier cette ressource", "false"));
    }


    @DeleteMapping("/delete-{id}")
    @Transactional
    public ResponseEntity<CreateResponseDto> deleteUser(@PathVariable Long id, @RequestBody AllUsersDto allUsersDto) {
        String token = allUsersDto.getAccessToken();
        String pass = allUsersDto.getPassword();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String password = jwtGenerator.extractPasswordFromJwt(token);

        UserEntity user = userService.getUser(id);
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("admin"));

        if (roles.contains("admin") && passwordEncoder.matches(pass, password) && !isAdmin && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            userService.deleteUser(id);
            return ResponseEntity.ok(new CreateResponseDto("user deleted", "true"));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CreateResponseDto("vous n'avez pas le droit de supprimer  cette ressource", "false"));
    }

    ////Calcule
    @PostMapping("/calculeAll")
    public ResponseEntity<CalculeResopnseDto> CalculeAllInSociete(@RequestBody CalculeDto calculeDto) {
        String token = calculeDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("admin") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            return ResponseEntity.ok(new CalculeResopnseDto(userService.CountEmployeeBySocietee(societe)));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new CalculeResopnseDto(-1));
        }
    }

    @PostMapping("/calculeByRole")
    public ResponseEntity<CalculeResopnseDto> CalculeByRole(@RequestBody CalculeDto calculeDto) {
        String token = calculeDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);///hne
        if (roles.contains("admin") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            return ResponseEntity.ok(new CalculeResopnseDto(userService.CountByRole(calculeDto.getRole(), societe)));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new CalculeResopnseDto(-1));
        }
    }

    @PostMapping("/calculeAllOnline")
    public ResponseEntity<CalculeResopnseDto> CalculeAllOnline(@RequestBody CalculeDto calculeDto) {
        String token = calculeDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("admin") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            return ResponseEntity.ok(new CalculeResopnseDto(userService.countOnlineUsersBySociete(societe)));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new CalculeResopnseDto(-1));
        }
    }

    @PostMapping("/calculeAdminOnline")
    public ResponseEntity<CalculeResopnseDto> CalculeAdminsOnline(@RequestBody CalculeDto calculeDto) {
        String token = calculeDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("admin") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            return ResponseEntity.ok(new CalculeResopnseDto(userService.countOnlineAdminUsers(societe)));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new CalculeResopnseDto(-1));
        }
    }

    @PostMapping("/calculeByEquipe")
    public ResponseEntity<CalculeResopnseDto> CalculateByEquipe(@RequestBody CalculeDto calculeDto) {
        String token = calculeDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("admin") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            return ResponseEntity.ok(new CalculeResopnseDto(userService.countTeamsByCompany(societe)));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new CalculeResopnseDto(-1));
        }
    }

    @GetMapping("/userPerformance")
    public ResponseEntity<List<UserPerformanceDto>> getUserPerformances(@RequestHeader ("Authorization") String token) {
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        if (roles.contains("director") && !blacklistedTokenService.isTokenBlacklisted(token)) {
            List<UserPerformanceDto> userPerformances = userService.getUsersPerformance();
            return ResponseEntity.ok(userPerformances);
        }else {
            return ResponseEntity.status(403).build();
        }
    }

    //Tri double sense selon critere choisi
    @PostMapping("/sorted")
    public ResponseEntity<ResponseAllUsersDto> getUsersSorted(@RequestBody TriDto triDto) {
        String token = triDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        String sortBy = triDto.getSelon();
        String order = triDto.getOrdre(); ///ASC - DESC
        if (roles.contains("admin") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            return ResponseEntity.ok(new ResponseAllUsersDto(userService.findAllUsersSorted(order, sortBy), "mnadem"));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseAllUsersDto("Vous n'avez pas le droit d'accéder à cette ressource"));
        }
    }

    //filters pour id ,nom ,prenom
    @PostMapping("/filter")
    public ResponseEntity<ResponseAllUsersDto> filterUsers(@RequestBody FilterDto filterDto) {
        String token = filterDto.getAccessToken();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        List<String> roles = jwtGenerator.extractRolesFromJwt(token);
        String societe = jwtGenerator.extractSocieteFromJwt(token);
        if (roles.contains("admin") && blacklistedTokenService.isTokenBlacklisted(token) == false) {
            String fieldName = filterDto.getFieldName();
            String value = filterDto.getValue();
            String operation = filterDto.getOperation();
            Specification<UserEntity> spec = null;
            switch (operation) {
                case "startsWith" -> spec = filtersService.startsWith(fieldName, value);
                case "contains" -> spec = filtersService.contains(fieldName, value);
                case "notContains" -> spec = filtersService.notContains(fieldName, value);
                case "endsWith" -> spec = filtersService.endsWith(fieldName, value);
                case "equals" -> spec = filtersService.equals(fieldName, value);
                case "notEquals" -> spec = filtersService.notEquals(fieldName, value);
                case "Online" ->{
                    if(value.equals("true")){
                        spec = filtersService.equals("online", true);
                    }else{
                        spec = filtersService.equals("online", false);
                    }
                }
                case "withRoles" -> {
                    List<String> rolesList = Arrays.asList(filterDto.getValue().split("|"));///// a verifier
                    spec = filtersService.withRoles(rolesList);
                }
                default -> throw new IllegalStateException("Unexpected value: " + operation);
            }
            if (spec != null) {
                return ResponseEntity.ok(new ResponseAllUsersDto(userRepository.findAll(spec), "mnadem"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseAllUsersDto("Cette opération n'existe pas"));
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseAllUsersDto("Vous n'avez pas le droit d'accéder à cette ressource"));
    }









}









