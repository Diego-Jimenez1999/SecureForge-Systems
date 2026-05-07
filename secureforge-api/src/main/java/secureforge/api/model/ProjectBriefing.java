package secureforge.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_briefings")
public class ProjectBriefing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private LocalDateTime briefingDate;

    public ProjectBriefing() {}

    public ProjectBriefing(Long id, String clientName, String description, LocalDateTime briefingDate) {
        this.id = id;
        this.clientName = clientName;
        this.description = description;
        this.briefingDate = briefingDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getBriefingDate() { return briefingDate; }
    public void setBriefingDate(LocalDateTime briefingDate) { this.briefingDate = briefingDate; }
}
