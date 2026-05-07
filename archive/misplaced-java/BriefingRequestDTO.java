package secureforge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BriefingRequestDTO {
    @NotBlank(message = "El nombre del cliente no puede estar vacío")
    private String clientName;
    @NotBlank(message = "La descripción del proyecto no puede estar vacía")
    private String description;
}