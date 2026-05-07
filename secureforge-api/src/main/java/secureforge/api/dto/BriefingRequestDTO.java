package secureforge.api.dto;

import jakarta.validation.constraints.NotBlank;

public class BriefingRequestDTO {
    @NotBlank(message = "El nombre del cliente no puede estar vacío")
    private String clientName;

    @NotBlank(message = "La descripción del proyecto no puede estar vacía")
    private String description;

    public BriefingRequestDTO() {}

    public BriefingRequestDTO(String clientName, String description) {
        this.clientName = clientName;
        this.description = description;
    }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
