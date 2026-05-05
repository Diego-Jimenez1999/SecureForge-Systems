package secureauth.config;

import java.time.LocalDateTime;

/**
 * POJO para solicitudes de proyecto/cotización.
 */
public class SolicitudProyecto {
    private Long id;
    private String clientName;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "SolicitudProyecto{" +
               "id=" + id +
               ", clientName='" + clientName + '\'' +
               ", status='" + status + '\'' +
               '}';
    }
}