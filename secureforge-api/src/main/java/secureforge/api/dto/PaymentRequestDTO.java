package secureforge.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PaymentRequestDTO {
    @NotBlank
    private String productName;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    public PaymentRequestDTO() {}

    public PaymentRequestDTO(String productName, String name, String email, String phone) {
        this.productName = productName;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
