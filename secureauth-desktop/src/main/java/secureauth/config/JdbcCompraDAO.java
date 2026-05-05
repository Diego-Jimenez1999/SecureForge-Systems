package secureauth.config;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementación JDBC básica para CompraDAO.
 */
public class JdbcCompraDAO implements CompraDAO {

    private static final Logger LOGGER = Logger.getLogger(JdbcCompraDAO.class.getName());
    private final String jdbcUrl;
    private final String dbUser;
    private final String dbPassword;

    public JdbcCompraDAO(String jdbcUrl, String dbUser, String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
    }

    @Override
    public void save(Compra compra) {
        String sql = "INSERT INTO compras (product_name, product_price, client_name, client_email, client_phone, comprobante_path, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, compra.getProductName());
            stmt.setBigDecimal(2, compra.getProductPrice());
            stmt.setString(3, compra.getClientName());
            stmt.setString(4, compra.getClientEmail());
            stmt.setString(5, compra.getClientPhone());
            stmt.setString(6, compra.getComprobantePath());
            stmt.setString(7, compra.getStatus());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    compra.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving purchase for product: " + compra.getProductName(), e);
            throw new RuntimeException("Failed to save purchase", e);
        }
    }
}