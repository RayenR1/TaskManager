package com.TaskManagement.Task.repository;

import com.TaskManagement.Task.models.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

//Optional<BlacklistedToken> deleteAll(BlacklistedTokenRepository tokens);
    List<BlacklistedToken> findByToken(String token);
Optional<BlacklistedToken> deleteByToken(String token);
}
