package secureforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import secureforge.dto.PaymentRequestDTO;
import secureforge.model.NequiPayment;
import secureforge.service.NequiPaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final NequiPaymentService nequiPaymentService;

    @Autowired
    public PaymentController(NequiPaymentService nequiPaymentService) {
        this.nequiPaymentService = nequiPaymentService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<NequiPayment> processPayment(
            @RequestPart("paymentData") PaymentRequestDTO paymentData,
            @RequestPart(value = "receipt", required = false) MultipartFile receiptFile) {
        try {
            NequiPayment newPayment = nequiPaymentService.processPayment(paymentData, receiptFile);
            return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}