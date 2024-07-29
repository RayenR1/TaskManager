package com.TaskManagement.Task.repository;

import com.TaskManagement.Task.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> , JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
   Optional<UserEntity> findById(Long id);
    Optional<UserEntity>deleteById(Long id);
    Boolean existsByEmail(String email);
    List<UserEntity> findAllBySocieteAndOnline(String societe, boolean online);
    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE u.id = :id AND r.name = :roleName")
    List<UserEntity> findAllByIdAndRoleName(Long id, String roleName);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.name = :roleName")
    List<UserEntity> findByRoleName(@Param("roleName") String roleName);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.name = :roleName")
    List<UserEntity> findAllByRoleName(@Param("roleName") String roleName);
}
