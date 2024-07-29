package com.TaskManagement.Task.service;

import com.TaskManagement.Task.models.BlacklistedToken;
import com.TaskManagement.Task.repository.BlacklistedTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlacklistedTokenService {
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    public BlacklistedTokenService(BlacklistedTokenRepository blacklistedTokenRepository) {
        this.blacklistedTokenRepository = blacklistedTokenRepository;
    }
    public boolean isTokenBlacklisted(String token) {
        List<BlacklistedToken> blacklistedTokens = blacklistedTokenRepository.findByToken(token);
        return blacklistedTokens.stream().anyMatch(blacklistedToken ->
                blacklistedToken.getExpiryDate().isAfter(LocalDateTime.now()));
    }



    ///normalment c'est un cron job qui s'execute chaque heure pour supprimer les tokens expirés
    @Scheduled(fixedRate = 3600000) // Every hour
    public void removeExpiredBlacklistedTokens() {
        List<BlacklistedToken> expiredTokens = blacklistedTokenRepository.findAll().stream()
                .filter(blacklistedToken -> blacklistedToken.getExpiryDate().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
        blacklistedTokenRepository.deleteAll(expiredTokens);
    }
}
