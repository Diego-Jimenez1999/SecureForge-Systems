package secureforge.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_briefings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectBriefing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    private LocalDateTime briefingDate;
}