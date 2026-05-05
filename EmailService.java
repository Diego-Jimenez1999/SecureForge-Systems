package secureauth.service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import secureauth.config.ApplicationConfig;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio para el envío de correos electrónicos.
 * Configurable a través de ApplicationConfig.
 *
 * @author Diego-Jimenez1999
 */
public class EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());
    private final ApplicationConfig config;
    private final Properties mailProperties;

    public EmailService(ApplicationConfig config) {
        this.config = config;
        this.mailProperties = new Properties();
        // Configuración SMTP básica
        mailProperties.put("mail.smtp.host", config.get("mail.smtp.host"));
        mailProperties.put("mail.smtp.port", config.get("mail.smtp.port"));
        mailProperties.put("mail.smtp.auth", config.get("mail.smtp.auth"));
        mailProperties.put("mail.smtp.starttls.enable", config.get("mail.smtp.starttls.enable"));
        mailProperties.put("mail.smtp.ssl.trust", config.get("mail.smtp.ssl.trust"));
        // Puedes añadir más propiedades según tu proveedor de correo
    }

    /**
     * Envía un correo electrónico.
     *
     * @param to      Destinatario
     * @param subject Asunto del correo
     * @param body    Cuerpo del correo (HTML o texto plano)
     */
    public void sendEmail(String to, String subject, String body) {
        String from = config.get("mail.from.address");
        String password = config.get("mail.from.password");

        Session session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8"); // O "text/plain"

            Transport.send(message);
            LOGGER.log(Level.INFO, "Correo enviado exitosamente a: {0} con asunto: {1}", new Object[]{to, subject});
        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, "Error al enviar correo a: " + to + " con asunto: " + subject, e);
            throw new RuntimeException("Error al enviar correo electrónico", e);
        }
    }

    /**
     * Envía un correo electrónico al administrador configurado.
     *
     * @param subject Asunto del correo
     * @param body    Cuerpo del correo
     */
    public void sendAdminEmail(String subject, String body) {
        sendEmail(config.get("mail.admin.address"), subject, body);
    }
}