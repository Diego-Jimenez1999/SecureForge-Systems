package secureauth.model;

/**
 * POJO de usuario del sistema.
 *
 * @author Diego-Jimenez1999
 */
public class User {
    private Long id;
    private String username;
    private String passwordHash;
    private String role;
    private int failedAttempts;
    private boolean locked;

    public Long getId() { return id; }
    public void setId(final Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(final String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(final String passwordHash) { this.passwordHash = passwordHash; }
    public String getRole() { return role; }
    public void setRole(final String role) { this.role = role; }
    public int getFailedAttempts() { return failedAttempts; }
    public void setFailedAttempts(final int failedAttempts) { this.failedAttempts = failedAttempts; }
    public boolean isLocked() { return locked; }
    public void setLocked(final boolean locked) { this.locked = locked; }
}
