package secureforge.api.identity.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import secureforge.api.identity.dto.*;
import secureforge.api.identity.model.Role;
import secureforge.api.identity.model.RoleName;
import secureforge.api.identity.model.UserAccount;
import secureforge.api.identity.repository.RoleRepository;
import secureforge.api.identity.repository.UserAccountRepository;

import java.util.List;

@Service
public class AuthService {

    private final UserAccountRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserAccountRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse register(AuthRegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El correo ya está en uso");
        }

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException("Rol base no inicializado"));

        UserAccount user = new UserAccount();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.getRoles().add(userRole);

        userRepository.save(user);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPasswordHash(), user.getRoles().stream()
                .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getName().name()))
                .toList());

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken, "Bearer", user.getUsername(), List.of(RoleName.ROLE_USER.name()));
    }

    public AuthResponse login(AuthLoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails principal = (UserDetails) auth.getPrincipal();
        UserAccount user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        String accessToken = jwtService.generateToken(principal);
        String refreshToken = refreshTokenService.createRefreshToken(user);
        List<String> roles = user.getRoles().stream().map(r -> r.getName().name()).toList();
        return new AuthResponse(accessToken, refreshToken, "Bearer", user.getUsername(), roles);
    }

    public AuthResponse refresh(TokenRefreshRequest request) {
        var token = refreshTokenService.verify(request.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("Refresh token inválido o expirado"));

        UserAccount user = token.getUser();
        UserDetails principal = new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPasswordHash(), user.getRoles().stream()
                .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getName().name()))
                .toList());

        String accessToken = jwtService.generateToken(principal);
        List<String> roles = user.getRoles().stream().map(r -> r.getName().name()).toList();
        return new AuthResponse(accessToken, request.getRefreshToken(), "Bearer", user.getUsername(), roles);
    }

    public void logout(TokenRefreshRequest request) {
        refreshTokenService.revoke(request.getRefreshToken());
    }
}
