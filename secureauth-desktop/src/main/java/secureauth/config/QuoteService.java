package secureauth.config;

import secureauth.service.EmailService;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio para manejar solicitudes de cotización de proyectos.
 */
public class QuoteService {

    private static final Logger LOGGER = Logger.getLogger(QuoteService.class.getName());
    private final SolicitudProyectoDAO solicitudProyectoDAO;
    private final EmailService emailService;

    public QuoteService(SolicitudProyectoDAO solicitudProyectoDAO, EmailService emailService) {
        this.solicitudProyectoDAO = solicitudProyectoDAO;
        this.emailService = emailService;
    }

    public void processQuoteRequest(String clientName, String description) {
        SolicitudProyecto solicitud = new SolicitudProyecto();
        solicitud.setClientName(clientName);
        solicitud.setDescription(description);
        solicitud.setStatus("PENDING");
        solicitud.setCreatedAt(LocalDateTime.now());
        solicitud.setUpdatedAt(LocalDateTime.now());

        solicitudProyectoDAO.save(solicitud);
        LOGGER.log(Level.INFO, "Solicitud de proyecto guardada: {0}", solicitud.getId());

        String adminEmailBody = String.format("<html><body><h3>Nueva Solicitud de Cotización desde SecureForge</h3><p><b>Cliente:</b> %s</p><p><b>Descripción:</b></p><p>%s</p><p>ID de Solicitud: %d</p></body></html>", clientName, description, solicitud.getId());
        emailService.sendAdminEmail("Nueva Solicitud de Cotización", adminEmailBody);
    }
}