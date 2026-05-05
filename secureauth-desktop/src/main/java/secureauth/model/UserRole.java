package secureauth.model;

import java.util.Locale;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private final String value;

    UserRole(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static UserRole from(final String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Rol invalido");
        }
        final String normalized = role.trim().toLowerCase(Locale.ROOT);
        for (UserRole userRole : values()) {
            if (userRole.value.equals(normalized)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Rol invalido: " + role);
    }
}
