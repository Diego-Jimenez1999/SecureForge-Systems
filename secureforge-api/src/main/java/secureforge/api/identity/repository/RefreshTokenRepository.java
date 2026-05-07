package secureforge.api.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import secureforge.api.identity.model.RefreshToken;
import secureforge.api.identity.model.UserAccount;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenHash(String tokenHash);
    void deleteByUser(UserAccount user);
}
