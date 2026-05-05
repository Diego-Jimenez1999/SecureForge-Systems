package secureauth.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación JDBC básica para MensajeContactoDAO.
 */
public class JdbcMensajeContactoDAO implements MensajeContactoDAO {

    private static final Logger LOGGER = Logger.getLogger(JdbcMensajeContactoDAO.class.getName());
    private final String jdbcUrl;
    private final String dbUser;
    private final String dbPassword;

    public JdbcMensajeContactoDAO(String jdbcUrl, String dbUser, String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
    }

    @Override
    public void save(MensajeContacto mensaje) {
        String sql = "INSERT INTO mensajes_contacto (nombre, email, asunto, mensaje) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, mensaje.getNombre());
            stmt.setString(2, mensaje.getEmail());
            stmt.setString(3, mensaje.getAsunto());
            stmt.setString(4, mensaje.getMensaje());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mensaje.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving contact message from: " + mensaje.getEmail(), e);
            throw new RuntimeException("Failed to save contact message", e);
        }
    }
}