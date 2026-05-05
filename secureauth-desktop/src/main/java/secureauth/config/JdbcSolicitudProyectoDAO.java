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
 * Implementación JDBC básica para SolicitudProyectoDAO.
 */
public class JdbcSolicitudProyectoDAO implements SolicitudProyectoDAO {

    private static final Logger LOGGER = Logger.getLogger(JdbcSolicitudProyectoDAO.class.getName());
    private final String jdbcUrl;
    private final String dbUser;
    private final String dbPassword;

    public JdbcSolicitudProyectoDAO(String jdbcUrl, String dbUser, String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
    }

    @Override
    public void save(SolicitudProyecto solicitud) {
        String sql = "INSERT INTO solicitudes_proyecto (client_name, description, status) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, solicitud.getClientName());
            stmt.setString(2, solicitud.getDescription());
            stmt.setString(3, solicitud.getStatus());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    solicitud.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving project request from: " + solicitud.getClientName(), e);
            throw new RuntimeException("Failed to save project request", e);
        }
    }
}