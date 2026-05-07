package secureforge.api.service;

import org.springframework.stereotype.Service;
import secureforge.api.dto.ContactRequestDTO;

@Service
public class ContactService {

    private final EmailService emailService;

    public ContactService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void processContact(ContactRequestDTO dto) {
        String subject = "Nuevo mensaje de contacto: " + dto.getSubject();
        String body = String.format(
                "Nombre: %s<br>Email: %s<br>Asunto: %s<br>Mensaje:<br>%s",
                dto.getName(), dto.getEmail(), dto.getSubject(), dto.getMessage());
        emailService.sendAdminEmail(subject, body);
    }
}
