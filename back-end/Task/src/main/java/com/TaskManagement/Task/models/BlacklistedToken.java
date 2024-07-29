package com.TaskManagement.Task.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "blacklisted_tokens")
public class BlacklistedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token", length = 1024)
    private String token;
    private LocalDateTime expiryDate;
}