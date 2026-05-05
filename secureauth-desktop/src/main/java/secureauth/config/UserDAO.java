package secureauth.config;

import secureauth.model.User;

/**
 * Interfaz para el acceso a datos de usuarios.
 */
public interface UserDAO {
    User findByUsername(String username);
    void save(User user);
    void updateAttemptsAndLock(Long userId, int attempts, boolean locked);
    void resetAttempts(Long userId);
}