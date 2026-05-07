package secureforge.api.service;

import org.springframework.stereotype.Service;
import secureforge.api.dto.BriefingRequestDTO;
import secureforge.api.model.ProjectBriefing;
import secureforge.api.repository.ProjectBriefingRepository;

import java.time.LocalDateTime;

@Service
public class ProjectBriefingService {

    private final ProjectBriefingRepository briefingRepository;
    private final EmailService emailService;

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

        String subject = "Nueva Solicitud de Cotización de Proyecto";
        String body = String.format(
                "Se ha recibido una nueva solicitud de cotización:<br>Cliente: %s<br>Descripción: %s",
                briefing.getClientName(), briefing.getDescription());
        emailService.sendAdminEmail(subject, body);

        return savedBriefing;
    }
}
