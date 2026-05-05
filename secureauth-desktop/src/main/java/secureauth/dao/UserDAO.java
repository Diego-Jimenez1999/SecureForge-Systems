package secureauth.dao;

import secureauth.model.User;
import secureauth.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO de usuarios. Solo acceso a datos.
 *
 * @author Diego-Jimenez1999
 */
public class UserDAO {
    private final DatabaseManager databaseManager;

    public UserDAO(final DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public User findByUsername(final String username) {
        final String sql = "SELECT id, username, password_hash, role, failed_attempts, locked FROM users WHERE username = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                final User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
                user.setFailedAttempts(rs.getInt("failed_attempts"));
                user.setLocked(rs.getBoolean("locked"));
                return user;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando usuario", ex);
        }
    }

    public void save(final User user) {
        final String sql = "INSERT INTO users (username, password_hash, role, failed_attempts, locked) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getFailedAttempts());
            ps.setBoolean(5, user.isLocked());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Error guardando usuario", ex);
        }
    }

    public void updateAttemptsAndLock(final Long userId, final int failedAttempts, final boolean locked) {
        final String sql = "UPDATE users SET failed_attempts = ?, locked = ? WHERE id = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, failedAttempts);
            ps.setBoolean(2, locked);
            ps.setLong(3, userId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Error actualizando intentos", ex);
        }
    }

    public void resetAttempts(final Long userId) {
        updateAttemptsAndLock(userId, 0, false);
    }
}
