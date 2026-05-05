package secureauth.service;

import secureauth.config.ApplicationConfig;
import secureauth.config.UserDAO;
import secureauth.model.User;
import secureauth.model.UserRole;
import secureauth.util.PasswordUtil;

/**
 * Servicio de autenticacion con reglas de negocio.
 *
 * @author Diego-Jimenez1999
 */
public class AuthService {

    private final UserDAO userDAO;
    private final int maxFailedAttempts;

    public AuthService(final UserDAO userDAO, final ApplicationConfig config) {
        this.userDAO = userDAO;
        this.maxFailedAttempts = config.getInt("security.maxFailedAttempts", 5);
    }

    public AuthResult login(final String username, final String password) {
        final User user = userDAO.findByUsername(username);
        if (user == null) {
            return AuthResult.failure("Credenciales invalidas");
        }
        if (user.isLocked()) {
            return AuthResult.failure("Cuenta bloqueada. Contacte al administrador.");
        }
        if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            final int attempts = user.getFailedAttempts() + 1;
            final boolean lock = attempts >= maxFailedAttempts;
            userDAO.updateAttemptsAndLock(user.getId(), attempts, lock);
            return AuthResult.failure(lock ? "Cuenta bloqueada por intentos fallidos" : "Credenciales invalidas");
        }

        userDAO.resetAttempts(user.getId());
        return AuthResult.success(user);
    }

    public void registerPublic(final String username, final String plainPassword) {
        registerWithRole(username, plainPassword, UserRole.USER);
    }

    public void registerPrivileged(final String username, final String plainPassword, final String role) {
        registerWithRole(username, plainPassword, UserRole.from(role));
    }

    private void registerWithRole(final String username, final String plainPassword, final UserRole role) {
        if (userDAO.findByUsername(username) != null) {
            throw new IllegalArgumentException("El usuario ya existe");
        }
        final User user = new User();
        user.setUsername(username);
        user.setPasswordHash(PasswordUtil.hashPassword(plainPassword));
        user.setRole(role.value());
        user.setFailedAttempts(0);
        user.setLocked(false);
        userDAO.save(user);
    }
}
