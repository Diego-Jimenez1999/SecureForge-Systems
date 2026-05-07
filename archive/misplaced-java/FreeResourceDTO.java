package secureforge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreeResourceDTO {
    private Long id;
    private String title;
    private String description;
    private String downloadUrl;
}