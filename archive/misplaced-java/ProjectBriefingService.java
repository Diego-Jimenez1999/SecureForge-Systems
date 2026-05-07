package secureforge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import secureforge.service.EmailService; 
import secureforge.dto.BriefingRequestDTO;
import secureforge.model.ProjectBriefing;
import secureforge.repository.ProjectBriefingRepository;

import java.time.LocalDateTime;

@Service
public class ProjectBriefingService {

    private final ProjectBriefingRepository briefingRepository;
    private final EmailService emailService; // Inyecta el EmailService

    @Autowired
    public ProjectBriefingService(ProjectBriefingRepository briefingRepository, EmailService emailService) {
        this.briefingRepository = briefingRepository;
        this.emailService = emailService;
    }

    public ProjectBriefing saveBriefing(BriefingRequestDTO briefingDto) {
        ProjectBriefing briefing = new ProjectBriefing();
        briefing.setClientName(briefingDto.getClientName());
        briefing.setDescription(briefingDto.getDescription());
        briefing.setBriefingDate(LocalDateTime.now());

        ProjectBriefing savedBriefing = briefingRepository.save(briefing);

        // Notificar al administrador sobre la nueva cotización
        String subject = "Nueva Solicitud de Cotización de Proyecto";
        String body = String.format("Se ha recibido una nueva solicitud de cotización:<br>Cliente: %s<br>Descripción: %s", briefing.getClientName(), briefing.getDescription());
        emailService.sendAdminEmail(subject, body);

        return savedBriefing;
    }
}