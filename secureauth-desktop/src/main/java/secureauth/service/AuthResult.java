package secureauth.service;

import secureauth.model.User;

/**
 * Resultado de autenticacion para desacoplar capa UI de logica.
 *
 * @author Diego-Jimenez1999
 */
public class AuthResult {
    private final boolean success;
    private final String message;
    private final User user;

    private AuthResult(final boolean success, final String message, final User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public static AuthResult success(final User user) { return new AuthResult(true, "OK", user); }
    public static AuthResult failure(final String message) { return new AuthResult(false, message, null); }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public User getUser() { return user; }
}
