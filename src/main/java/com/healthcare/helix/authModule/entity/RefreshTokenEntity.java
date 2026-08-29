package com.healthcare.helix.authModule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "userId", nullable = false)
    private UUID userId;  // User Owner

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @Column(nullable = true, length = 200)
    private String deviceName;

    @Column(nullable = true, length = 250)
    private String deviceId;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = true)
    private String operatingSystem;

    @Column(nullable = true)
    private String browser;

    @Column(nullable = true)
    private String ipAddress;

    @Column(nullable = false)
    private boolean revoked = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
