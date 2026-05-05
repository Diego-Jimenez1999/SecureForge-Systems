package secureauth.config;

import secureauth.model.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación JDBC básica para UserDAO.
 * NOTA: Esto es un ejemplo. En producción, considera usar un ORM como Hibernate/JPA.
 */
public class JdbcUserDAO implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(JdbcUserDAO.class.getName());
    private final String jdbcUrl;
    private final String dbUser;
    private final String dbPassword;

    public JdbcUserDAO(String jdbcUrl, String dbUser, String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // O el driver de tu DB
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "JDBC Driver not found", e);
            throw new RuntimeException("Failed to load JDBC driver", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password_hash, role, failed_attempts, locked FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setRole(rs.getString("role"));
                    user.setFailedAttempts(rs.getInt("failed_attempts"));
                    user.setLocked(rs.getBoolean("locked"));
                    return user;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding user by username: " + username, e);
        }
        return null;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (username, password_hash, role, failed_attempts, locked) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getFailedAttempts());
            stmt.setBoolean(5, user.isLocked());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving user: " + user.getUsername(), e);
            throw new RuntimeException("Failed to save user", e);
        }
    }

    @Override
    public void updateAttemptsAndLock(Long userId, int attempts, boolean locked) {
        String sql = "UPDATE users SET failed_attempts = ?, locked = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, attempts);
            stmt.setBoolean(2, locked);
            stmt.setLong(3, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating attempts and lock for user: " + userId, e);
        }
    }

    @Override
    public void resetAttempts(Long userId) {
        String sql = "UPDATE users SET failed_attempts = 0, locked = FALSE WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error resetting attempts for user: " + userId, e);
        }
    }
}