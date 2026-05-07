package secureforge.api.dto;

public class FreeResourceDTO {
    private Long id;
    private String title;
    private String description;
    private String downloadUrl;

    public FreeResourceDTO() {}

    public FreeResourceDTO(Long id, String title, String description, String downloadUrl) {
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
