package secureforge.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "free_resources")
public class FreeResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String downloadUrl;

    public FreeResource() {}

    public FreeResource(Long id, String title, String description, String downloadUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.downloadUrl = downloadUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }
}
