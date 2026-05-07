package secureforge.api.identity.dto;

import java.util.List;

public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String username;
    private List<String> roles;

    public AuthResponse(String accessToken, String refreshToken, String tokenType, String username, List<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.username = username;
        this.roles = roles;
    }

    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public String getTokenType() { return tokenType; }
    public String getUsername() { return username; }
    public List<String> getRoles() { return roles; }
}
