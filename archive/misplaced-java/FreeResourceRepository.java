package secureforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import secureforge.model.FreeResource;

@Repository
public interface FreeResourceRepository extends JpaRepository<FreeResource, Long> {
}