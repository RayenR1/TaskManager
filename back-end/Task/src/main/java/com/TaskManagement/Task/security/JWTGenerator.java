package com.TaskManagement.Task.security;

import com.TaskManagement.Task.models.UserEntity;
import com.TaskManagement.Task.models.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import java.nio.file.attribute.UserPrincipal;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTGenerator {
    private final Key key= Keys.hmacShaKeyFor(SecurityConstants.JWTsecret.getBytes());


    public  String generateToken (Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = userDetails.getUserEntity();
        int  id = user.getId();
        String email = user.getEmail();
        String societe = user.getSociete();
        List<Integer> taches = user.getTaches().stream().map(tache::getId).collect(Collectors.toList());
        List<Integer> equipes = user.getEquipes().stream().map(Equipe::getId).collect(Collectors.toList());
        String username =authentication.getName();
        Date currentDate =new Date();
        Date expireDate = new Date(currentDate.getTime()+SecurityConstants.JWTexpiration);
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .claim("id", id)
                .claim("email", email)
                .claim("societe", societe)
                .claim("taches", taches)
                .claim("equipes", equipes)
                .claim("password", user.getPassword())
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public  String getUsernameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public List<String> extractRolesFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Récupérer les rôles à partir des claims
        List<String> roles = (List<String>) claims.get("roles");

        return roles;
    }

    public String extractSocieteFromJwt(String token) {
        // Supprimez le préfixe "Bearer " du token s'il est présent
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Analysez le token et extrayez les revendications (claims)
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Extrait la propriété "societe" des revendications
        return claims.get("societe", String.class);
    }
    public int extractIdFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return (int) claims.get("id");
    }
    public String extractPasswordFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Extrait la propriété "password" des revendications
        return claims.get("password", String.class);
    }

    public List<Integer> extractTachesFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return (List<Integer>) claims.get("taches");
    }
    public List<Integer> extractEquipesFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return (List<Integer>) claims.get("equipes");
    }
    public String extractEmailFromJwt(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("email", String.class);
    }
    public boolean validdateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.JWTsecret).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

}
