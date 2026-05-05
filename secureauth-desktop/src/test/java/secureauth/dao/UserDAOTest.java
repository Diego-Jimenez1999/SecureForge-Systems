package secureauth.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import secureauth.config.ApplicationConfig;
import secureauth.model.User;
import secureauth.util.DatabaseManager;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() throws Exception {
        final Properties properties = new Properties();
        properties.setProperty("db.url", "jdbc:h2:mem:secureauth;MODE=MySQL;DB_CLOSE_DELAY=-1");
        properties.setProperty("db.username", "sa");
        properties.setProperty("db.password", "");

        final DatabaseManager manager = new DatabaseManager(ApplicationConfig.fromProperties(properties));
        userDAO = new UserDAO(manager);

        try (Connection connection = manager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
            statement.execute("""
                    CREATE TABLE users (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        username VARCHAR(80) NOT NULL UNIQUE,
                        password_hash VARCHAR(100) NOT NULL,
                        role VARCHAR(10) NOT NULL DEFAULT 'user',
                        failed_attempts INT NOT NULL DEFAULT 0,
                        locked BOOLEAN NOT NULL DEFAULT FALSE
                    )
                    """);
        }
    }

    @Test
    void saveAndFindByUsername() {
        final User user = new User();
        user.setUsername("ana");
        user.setPasswordHash("hash");
        user.setRole("user");
        user.setFailedAttempts(0);
        user.setLocked(false);

        userDAO.save(user);
        final User stored = userDAO.findByUsername("ana");

        assertNotNull(stored);
        assertEquals("ana", stored.getUsername());
        assertEquals("user", stored.getRole());
    }

    @Test
    void updateAttemptsAndReset() {
        final User user = new User();
        user.setUsername("lockedUser");
        user.setPasswordHash("hash");
        user.setRole("user");
        user.setFailedAttempts(0);
        user.setLocked(false);
        userDAO.save(user);

        final User stored = userDAO.findByUsername("lockedUser");
        assertNotNull(stored);

        userDAO.updateAttemptsAndLock(stored.getId(), 3, true);
        final User updated = userDAO.findByUsername("lockedUser");
        assertNotNull(updated);
        assertEquals(3, updated.getFailedAttempts());
        assertTrue(updated.isLocked());

        userDAO.resetAttempts(updated.getId());
        final User reset = userDAO.findByUsername("lockedUser");
        assertNotNull(reset);
        assertEquals(0, reset.getFailedAttempts());
        assertFalse(reset.isLocked());
    }

    @Test
    void findByUsernameReturnsNullForUnknownUser() {
        assertNull(userDAO.findByUsername("missing"));
    }
}
