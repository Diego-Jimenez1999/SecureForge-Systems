package secureforge.api.identity.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import secureforge.api.identity.model.RefreshToken;
import secureforge.api.identity.model.UserAccount;
import secureforge.api.identity.repository.RefreshTokenRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final long refreshTokenExpirationDays;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            org.springframework.core.env.Environment env) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenExpirationDays = Long.parseLong(env.getProperty("security.jwt.refresh-token-expiration-days", "7"));
    }

    public String createRefreshToken(UserAccount user) {
        String rawToken = UUID.randomUUID() + "." + UUID.randomUUID();

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setTokenHash(hash(rawToken));
        token.setExpiresAt(LocalDateTime.now().plusDays(refreshTokenExpirationDays));
        token.setRevoked(false);
        refreshTokenRepository.save(token);

        return rawToken;
    }

    public Optional<RefreshToken> verify(String rawToken) {
        String hash = hash(rawToken);
        Optional<RefreshToken> token = refreshTokenRepository.findByTokenHash(hash);
        if (token.isEmpty()) {
            return Optional.empty();
        }
        RefreshToken value = token.get();
        if (value.isRevoked() || value.getExpiresAt().isBefore(LocalDateTime.now())) {
            return Optional.empty();
        }
        return token;
    }

    public void revoke(String rawToken) {
        String hash = hash(rawToken);
        refreshTokenRepository.findByTokenHash(hash).ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        });
    }

    private String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(value.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 no disponible", ex);
        }
    }
}
