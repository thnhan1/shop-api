package com.nhanab.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "verification_token", uniqueConstraints = @UniqueConstraint(columnNames = {"identifier", "token"}))
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String identifier;

    @Column(unique = true)
    private String token;

    private LocalDateTime expires;
}
