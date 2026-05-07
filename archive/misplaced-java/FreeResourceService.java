package secureforge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import secureforge.dto.FreeResourceDTO;
import secureforge.model.FreeResource;
import secureforge.repository.FreeResourceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeResourceService {

    private final FreeResourceRepository resourceRepository;

    @Autowired
    public FreeResourceService(FreeResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<FreeResourceDTO> getAllResources() {
        return resourceRepository.findAll().stream()
                .map(resource -> new FreeResourceDTO(resource.getId(), resource.getTitle(), resource.getDescription(), resource.getDownloadUrl()))
                .collect(Collectors.toList());
    }
}