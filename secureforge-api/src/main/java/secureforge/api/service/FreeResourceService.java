package secureforge.api.service;

import org.springframework.stereotype.Service;
import secureforge.api.dto.FreeResourceDTO;
import secureforge.api.repository.FreeResourceRepository;

import java.util.List;

@Service
public class FreeResourceService {

    private final FreeResourceRepository resourceRepository;

    public FreeResourceService(FreeResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<FreeResourceDTO> getAllResources() {
        return resourceRepository.findAll().stream()
                .map(resource -> new FreeResourceDTO(resource.getId(), resource.getTitle(), resource.getDescription(), resource.getDownloadUrl()))
                .toList();
    }
}
