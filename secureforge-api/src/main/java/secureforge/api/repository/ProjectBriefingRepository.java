package secureforge.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import secureforge.api.model.ProjectBriefing;

@Repository
public interface ProjectBriefingRepository extends JpaRepository<ProjectBriefing, Long> {
}
