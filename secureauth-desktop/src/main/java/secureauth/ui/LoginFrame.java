package secureauth.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import secureauth.controller.AuthController;
import secureauth.service.AuthResult;

/**
 * Pantalla de login desacoplada de acceso a datos.
 *
 * @author Diego-Jimenez1999
 */
public class LoginFrame extends JFrame {

    private final AuthController authController;

    public LoginFrame(final AuthController authController) {
        this.authController = authController;
        UiTheme.apply(); // Asegura que los estilos se apliquen a los componentes
        setTitle("SecureAuth Desktop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 280);
        setLocationRelativeTo(null);
        setContentPane(buildContent());
    }

    private JPanel buildContent() {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UiTheme.BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        final JLabel userLabel = new JLabel("Usuario");
        final JTextField userField = new JTextField();
        userField.setPreferredSize(new Dimension(220, 32));

        final JLabel passLabel = new JLabel("Password");
        final JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(220, 32));

        final JButton loginButton = new JButton("Iniciar Sesion");
        loginButton.setBackground(UiTheme.PRIMARY);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                loginButton.setBackground(UiTheme.PRIMARY_HOVER);
            }

            @Override
            public void mouseExited(final MouseEvent e) {
                loginButton.setBackground(UiTheme.PRIMARY);
            }
        });

        loginButton.addActionListener(e -> {
            final AuthResult result = authController.login(
                    userField.getText().trim(),
                    new String(passField.getPassword())
            );
            if (result.isSuccess()) {
                JOptionPane.showMessageDialog(this,
                        "Bienvenido " + result.getUser().getUsername() + " (" + result.getUser().getRole() + ")");
            } else {
                JOptionPane.showMessageDialog(this, result.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0; gbc.gridy = 0; panel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(passField, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(loginButton, gbc);

        return panel;
    }
}
