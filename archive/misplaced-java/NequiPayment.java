package secureforge.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "nequi_payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NequiPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String clientEmail;

    private String clientPhone;
    private String receiptPath; // Ruta donde se guardará el comprobante
    private LocalDateTime paymentDate;
}