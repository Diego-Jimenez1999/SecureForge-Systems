package secureauth.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utilidad de hashing seguro para passwords.
 *
 * @author Diego-Jimenez1999
 */
public final class PasswordUtil {

    private PasswordUtil() {
    }

    public static String hashPassword(final String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean verifyPassword(final String plainPassword, final String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
