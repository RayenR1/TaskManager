package com.TaskManagement.Task.security;


import com.TaskManagement.Task.models.CustomUserDetails;
import com.TaskManagement.Task.models.UserEntity;
import com.TaskManagement.Task.models.UserRole;
import com.TaskManagement.Task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    public UserRepository userRepository;  //injection de dependance
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("email not found"));
        //return new User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
        return new CustomUserDetails(user, mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<UserRole> roles ){
        return  roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
