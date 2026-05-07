package secureforge.api.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import secureforge.api.dto.PaymentRequestDTO;
import secureforge.api.model.NequiPayment;
import secureforge.api.service.NequiPaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final NequiPaymentService nequiPaymentService;

    public PaymentController(NequiPaymentService nequiPaymentService) {
        this.nequiPaymentService = nequiPaymentService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<NequiPayment> processPayment(
            @Valid @RequestPart("paymentData") PaymentRequestDTO paymentData,
            @RequestPart(value = "receipt", required = false) MultipartFile receiptFile) {
        NequiPayment newPayment = nequiPaymentService.processPayment(paymentData, receiptFile);
        return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
    }
}
