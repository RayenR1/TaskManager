package com.TaskManagement.Task.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
        super(userEntity.getEmail(), userEntity.getPassword(), authorities);
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}
