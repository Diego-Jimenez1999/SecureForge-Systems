package secureforge.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import secureforge.api.dto.FreeResourceDTO;
import secureforge.api.service.FreeResourceService;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final FreeResourceService resourceService;

    public ResourceController(FreeResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<List<FreeResourceDTO>> getFreeResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }
}
