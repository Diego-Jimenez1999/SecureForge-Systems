package secureauth;

import secureauth.config.ApplicationConfig;
import secureauth.controller.AuthController;
import secureauth.dao.UserDAO;
import secureauth.service.AuthService;
import secureauth.ui.LoginFrame;
import secureauth.ui.UiTheme;
import secureauth.util.DatabaseManager;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicacion SecureAuth Desktop.
 *
 * <p>Este bootstrap inicializa dependencias por capas sin acoplar la UI con DAO.</p>
 *
 * @author Diego-Jimenez1999
 * @since 1.0.0
 * @example mvn exec:java
 */
public final class SecureAuthApplication {

    private SecureAuthApplication() {
    }

    /**
     * Inicia la aplicacion y muestra la pantalla de login.
     *
     * @param args argumentos de consola
     */
    public static void main(final String[] args) {
        final ApplicationConfig config = ApplicationConfig.load();
        final DatabaseManager databaseManager = new DatabaseManager(config);

        final UserDAO userDAO = new UserDAO(databaseManager);
        final AuthService authService = new AuthService(userDAO, config);
        final AuthController authController = new AuthController(authService);

        SwingUtilities.invokeLater(() -> {
            UiTheme.apply();
            new LoginFrame(authController).setVisible(true);
        });
    }
}
