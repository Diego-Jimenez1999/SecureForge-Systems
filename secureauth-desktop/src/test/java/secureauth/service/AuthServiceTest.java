package secureauth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import secureauth.config.ApplicationConfig;
import secureauth.dao.UserDAO;
import secureauth.model.User;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthServiceTest {

    private FakeUserDAO userDAO;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userDAO = new FakeUserDAO();
        final Properties properties = new Properties();
        properties.setProperty("security.maxFailedAttempts", "3");
        authService = new AuthService(userDAO, ApplicationConfig.fromProperties(properties));
    }

    @Test
    void loginSuccessResetsAttempts() {
        userDAO.byUsername = buildUser(1L, 2, false);

        final AuthResult result = authService.login("diego", "secret123");

        assertTrue(result.isSuccess());
        assertEquals(1L, userDAO.resetAttemptsUserId);
        assertNull(userDAO.lastUpdatedUserId);
    }

    @Test
    void loginFailureIncrementsAttempts() {
        userDAO.byUsername = buildUser(1L, 1, false);

        final AuthResult result = authService.login("diego", "wrong-password");

        assertFalse(result.isSuccess());
        assertEquals(1L, userDAO.lastUpdatedUserId);
        assertEquals(2, userDAO.lastFailedAttempts);
        assertFalse(userDAO.lastLocked);
    }

    @Test
    void loginFailureLocksAccountAtThreshold() {
        userDAO.byUsername = buildUser(1L, 2, false);

        final AuthResult result = authService.login("diego", "wrong-password");

        assertFalse(result.isSuccess());
        assertEquals("Cuenta bloqueada por intentos fallidos", result.getMessage());
        assertEquals(1L, userDAO.lastUpdatedUserId);
        assertEquals(3, userDAO.lastFailedAttempts);
        assertTrue(userDAO.lastLocked);
    }

    @Test
    void registerPublicForcesUserRole() {
        authService.registerPublic("new-user", "password123");

        assertEquals("user", userDAO.savedUser.getRole());
        assertEquals("new-user", userDAO.savedUser.getUsername());
    }

    private static User buildUser(final Long id, final int attempts, final boolean locked) {
        final User user = new User();
        user.setId(id);
        user.setUsername("diego");
        user.setPasswordHash(secureauth.util.PasswordUtil.hashPassword("secret123"));
        user.setRole("user");
        user.setFailedAttempts(attempts);
        user.setLocked(locked);
        return user;
    }

    private static final class FakeUserDAO extends UserDAO {
        private User byUsername;
        private User savedUser;
        private Long lastUpdatedUserId;
        private int lastFailedAttempts;
        private boolean lastLocked;
        private Long resetAttemptsUserId;

        private FakeUserDAO() {
            super(null);
        }

        @Override
        public User findByUsername(final String username) {
            if (savedUser != null && savedUser.getUsername().equals(username)) {
                return savedUser;
            }
            return byUsername;
        }

        @Override
        public void save(final User user) {
            savedUser = user;
        }

        @Override
        public void updateAttemptsAndLock(final Long userId, final int failedAttempts, final boolean locked) {
            lastUpdatedUserId = userId;
            lastFailedAttempts = failedAttempts;
            lastLocked = locked;
        }

        @Override
        public void resetAttempts(final Long userId) {
            resetAttemptsUserId = userId;
        }
    }
}
