package secureauth.config;

import secureauth.service.EmailService;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio para manejar el flujo de compra de productos/servicios.
 */
public class PurchaseService {

    private static final Logger LOGGER = Logger.getLogger(PurchaseService.class.getName());
    private final CompraDAO compraDAO;
    private final EmailService emailService;
    private final String uploadDir; // Directorio para guardar comprobantes

    public PurchaseService(CompraDAO compraDAO, EmailService emailService, String uploadDir) {
        this.compraDAO = compraDAO;
        this.emailService = emailService;
        this.uploadDir = uploadDir;
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not create upload directory: " + uploadDir, e);
            throw new RuntimeException("Failed to initialize upload directory", e);
        }
    }

    public void processPurchase(String productName, BigDecimal productPrice, String clientName, String clientEmail, String clientPhone, InputStream comprobanteStream, String originalFileName) {
        String comprobantePath = null;
        if (comprobanteStream != null && originalFileName != null && !originalFileName.isEmpty()) {
            try {
                String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                Path filePath = Paths.get(uploadDir, uniqueFileName);
                Files.copy(comprobanteStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                comprobantePath = filePath.toString();
                LOGGER.log(Level.INFO, "Comprobante guardado en: {0}", comprobantePath);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error al guardar el comprobante para " + clientEmail, e);
            }
        }

        Compra compra = new Compra();
        compra.setProductName(productName);
        compra.setProductPrice(productPrice);
        compra.setClientName(clientName);
        compra.setEmail(clientEmail);
        compra.setClientPhone(clientPhone);
        compra.setComprobantePath(comprobantePath);
        compra.setStatus("PENDING_PAYMENT");
        compra.setCreatedAt(LocalDateTime.now());
        compra.setUpdatedAt(LocalDateTime.now());
        compraDAO.save(compra);
        LOGGER.log(Level.INFO, "Compra guardada: {0}", compra.getId());

        String adminEmailBody = String.format("<html><body><h3>Nueva Solicitud de Compra desde SecureForge</h3><p><b>Producto:</b> %s</p><p><b>Precio:</b> %s</p><p><b>Cliente:</b> %s (%s)</p><p><b>Teléfono:</b> %s</p><p><b>Comprobante:</b> %s</p><p>ID de Compra: %d</p></body></html>", productName, productPrice.toPlainString(), clientName, clientEmail, clientPhone, (comprobantePath != null ? "Disponible en: " + comprobantePath : "No adjuntado"), compra.getId());
        emailService.sendAdminEmail("Nueva Solicitud de Compra: " + productName, adminEmailBody);
    }
}