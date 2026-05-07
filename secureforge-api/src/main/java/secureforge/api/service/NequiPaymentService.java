package secureforge.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import secureforge.api.dto.PaymentRequestDTO;
import secureforge.api.model.NequiPayment;
import secureforge.api.repository.NequiPaymentRepository;

import java.time.LocalDateTime;

@Service
public class NequiPaymentService {

    private final NequiPaymentRepository nequiPaymentRepository;
    private final FileStorageService fileStorageService;
    private final EmailService emailService;

    public NequiPaymentService(NequiPaymentRepository nequiPaymentRepository,
                               FileStorageService fileStorageService,
                               EmailService emailService) {
        this.nequiPaymentRepository = nequiPaymentRepository;
        this.fileStorageService = fileStorageService;
        this.emailService = emailService;
    }

    public NequiPayment processPayment(PaymentRequestDTO paymentDto, MultipartFile receiptFile) {
        String receiptPath = null;
        if (receiptFile != null && !receiptFile.isEmpty()) {
            receiptPath = fileStorageService.storeFile(receiptFile);
        }

        NequiPayment payment = new NequiPayment();
        payment.setProductName(paymentDto.getProductName());
        payment.setClientName(paymentDto.getName());
        payment.setClientEmail(paymentDto.getEmail());
        payment.setClientPhone(paymentDto.getPhone());
        payment.setReceiptPath(receiptPath);
        payment.setPaymentDate(LocalDateTime.now());

        NequiPayment savedPayment = nequiPaymentRepository.save(payment);

        String subject = "Nueva Compra Registrada: " + payment.getProductName();
        String body = String.format(
                "Nueva compra:<br>Producto: %s<br>Cliente: %s (%s)<br>Teléfono: %s<br>Comprobante: %s",
                payment.getProductName(),
                payment.getClientName(),
                payment.getClientEmail(),
                payment.getClientPhone(),
                receiptPath != null ? "Sí (" + receiptPath + ")" : "No");
        emailService.sendAdminEmail(subject, body);

        return savedPayment;
    }
}
