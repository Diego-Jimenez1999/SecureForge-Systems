package secureauth.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * POJO para compras de productos/servicios.
 */
public class Compra {
    private Long id;
    private String productName;
    private BigDecimal productPrice;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private String comprobantePath;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public BigDecimal getProductPrice() { return productPrice; }
    public void setProductPrice(BigDecimal productPrice) { this.productPrice = productPrice; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getClientEmail() { return clientEmail; }
    public void setEmail(String clientEmail) { this.clientEmail = clientEmail; }

    public String getClientPhone() { return clientPhone; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }

    public String getComprobantePath() { return comprobantePath; }
    public void setComprobantePath(String comprobantePath) { this.comprobantePath = comprobantePath; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Compra{" +
               "id=" + id +
               ", productName='" + productName + '\'' +
               ", productPrice=" + productPrice +
               ", clientName='" + clientName + '\'' +
               ", clientEmail='" + clientEmail + '\'' +
               ", clientPhone='" + clientPhone + '\'' +
               ", comprobantePath='" + comprobantePath + '\'' +
               ", status='" + status + '\'' +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               '}';
    }
}