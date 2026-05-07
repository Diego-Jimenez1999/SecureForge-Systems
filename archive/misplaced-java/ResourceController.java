package secureforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import secureforge.dto.FreeResourceDTO;
import secureforge.service.FreeResourceService;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final FreeResourceService resourceService;

    @Autowired
    public ResourceController(FreeResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<List<FreeResourceDTO>> getFreeResources() {
        List<FreeResourceDTO> resources = resourceService.getAllResources();
        return ResponseEntity.ok(resources);
    }
}