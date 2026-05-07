package secureforge.api.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import secureforge.api.identity.model.Role;
import secureforge.api.identity.model.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
