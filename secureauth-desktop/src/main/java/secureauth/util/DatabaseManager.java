package secureauth.util;

import secureauth.config.ApplicationConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza la creacion de conexiones JDBC.
 *
 * @author Diego-Jimenez1999
 */
public final class DatabaseManager {

    private final ApplicationConfig config;

    public DatabaseManager(final ApplicationConfig config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                config.get("db.url"),
                config.get("db.username"),
                config.get("db.password")
        );
    }
}
