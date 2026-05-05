package secureauth.ui;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;

/**
 * Define el tema visual reutilizable para toda la app.
 *
 * @author Diego-Jimenez1999
 */
public final class UiTheme {

    public static final Color BG = new Color(245, 247, 250);
    public static final Color PRIMARY = new Color(18, 98, 164);
    public static final Color PRIMARY_HOVER = new Color(14, 80, 136);
    public static final Color TEXT = new Color(30, 37, 44);

    private UiTheme() {
    }

    public static void apply() {
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("PasswordField.font", new Font("Segoe UI", Font.PLAIN, 14));
    }
}
