package com.nhanab.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "account", uniqueConstraints = @UniqueConstraint(columnNames = {"provider", "provider_account_id"}))
public class Account {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String type;
    private String provider;

    @Column(name = "provider_account_id", columnDefinition = "uuid")
    private UUID providerAccountId;

    @Lob
    private String refreshToken;

    @Lob
    private String accessToken;

    private Integer expiresAt;
    private String tokenType;
    private String scope;

    @Lob
    private String idToken;
    private String sessionState;

    @Lob
    private String oauthToken;

    @Lob
    private String oauthTokenSecret;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}