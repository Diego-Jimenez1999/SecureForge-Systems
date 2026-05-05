package secureauth.controller;

import secureauth.service.AuthResult;
import secureauth.service.AuthService;

/**
 * Controlador de autenticacion (UI -> Service).
 *
 * @author Diego-Jimenez1999
 */
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    public AuthResult login(final String username, final String password) {
        return authService.login(username, password);
    }

    public void registerPublic(final String username, final String password) {
        authService.registerPublic(username, password);
    }

    public void registerPrivileged(final String username, final String password, final String role) {
        authService.registerPrivileged(username, password, role);
    }
}
