package com.TaskManagement.Task.repository;

import com.TaskManagement.Task.models.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole,Integer> {
    Optional<UserRole> findByName(String name);//requet select
    @Transactional
    void deleteById(Long userId);
}
