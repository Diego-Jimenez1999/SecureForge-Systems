package secureforge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    private String productName;
    private String name;
    private String email;
    private String phone;
    // No se incluye el archivo aquí, se manejará como MultipartFile en el controlador
}