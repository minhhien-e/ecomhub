package ecomhub.authservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false)
    private String userAgent;
    @Column(nullable = false)
    private String ipAddress;
    @Column(nullable = false)
    private LocalDateTime issuedAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private boolean revoked;
    private LocalDateTime revokedAt;
    private UUID revokedBy;
    //mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
}
