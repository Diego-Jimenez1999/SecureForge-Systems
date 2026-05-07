package secureforge.api.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromAddress;
    private final String adminAddress;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${mail.from.address}") String fromAddress,
            @Value("${mail.admin.address}") String adminAddress) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
        this.adminAddress = adminAddress;
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (Exception ex) {
            throw new IllegalStateException("Error al enviar correo", ex);
        }
    }

    public void sendAdminEmail(String subject, String body) {
        sendEmail(adminAddress, subject, body);
    }
}
