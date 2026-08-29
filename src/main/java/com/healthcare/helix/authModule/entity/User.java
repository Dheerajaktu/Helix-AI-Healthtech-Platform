package com.healthcare.helix.authModule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


    @Entity
    @Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(nullable = false)
        private String firstName;

        private String lastName;

        @Column(nullable = false, unique = true, length = 100)
        private String email;

        @Column(nullable = false, unique = true, length = 12)
        private String mobileNumber;

        @Column(nullable = false)
        private LocalDate dateOfBirth;

        @Column(nullable = false, length = 10)
        private String gender;
        /*
         * length = 255 because after encode,
         * it will go high, and in future if
         * I change algorithm, then schema will not break.
         */
        @Column(nullable = false, length = 255)
        private String password;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 30)
        private Role role;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 30)
        private AccountStatus accountStatus;

        @Column(nullable = false)
        private boolean emailVerified;

        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @Column(nullable = false)
        private LocalDateTime updatedAt;

        @PrePersist
        public void onCreate() {
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();

            if (this.role == null) this.role = Role.USER;
            if (this.accountStatus == null) this.accountStatus = AccountStatus.ACTIVE;
            this.emailVerified = false;
        }

        @PreUpdate
        public void onUpdate() {
            this.updatedAt = LocalDateTime.now();
        }

    }


