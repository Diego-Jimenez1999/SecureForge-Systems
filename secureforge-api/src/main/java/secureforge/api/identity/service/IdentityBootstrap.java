package secureforge.api.identity.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import secureforge.api.identity.model.Role;
import secureforge.api.identity.model.RoleName;
import secureforge.api.identity.model.UserAccount;
import secureforge.api.identity.repository.RoleRepository;
import secureforge.api.identity.repository.UserAccountRepository;

@Component
public class IdentityBootstrap implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public IdentityBootstrap(RoleRepository roleRepository,
                             UserAccountRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        for (RoleName roleName : RoleName.values()) {
            roleRepository.findByName(roleName).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleName);
                return roleRepository.save(role);
            });
        }

        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN no existe"));
            UserAccount admin = new UserAccount();
            admin.setUsername("admin");
            admin.setEmail("admin@secureforge.local");
            admin.setPasswordHash(passwordEncoder.encode("SecureForgeAdmin123!"));
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
        }
    }
}
