package secureauth.config;

import secureauth.service.EmailService;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio para manejar mensajes de contacto.
 */
public class ContactService {

    private static final Logger LOGGER = Logger.getLogger(ContactService.class.getName());
    private final MensajeContactoDAO mensajeContactoDAO;
    private final EmailService emailService;

    public ContactService(MensajeContactoDAO mensajeContactoDAO, EmailService emailService) {
        this.mensajeContactoDAO = mensajeContactoDAO;
        this.emailService = emailService;
    }

    public void processContactMessage(String name, String email, String subject, String message) {
        MensajeContacto contacto = new MensajeContacto();
        contacto.setNombre(name);
        contacto.setEmail(email);
        contacto.setAsunto(subject);
        contacto.setMensaje(message);
        contacto.setCreatedAt(LocalDateTime.now());

        mensajeContactoDAO.save(contacto);
        LOGGER.log(Level.INFO, "Mensaje de contacto guardado: {0}", contacto.getId());

        String adminEmailBody = String.format("<html><body><h3>Nuevo Mensaje de Contacto desde SecureForge</h3><p><b>De:</b> %s (%s)</p><p><b>Asunto:</b> %s</p><p><b>Mensaje:</b></p><p>%s</p></body></html>", name, email, subject, message);
        emailService.sendAdminEmail("Nuevo Mensaje de Contacto: " + subject, adminEmailBody);
    }
}