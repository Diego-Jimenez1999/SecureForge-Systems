package secureforge.api.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import secureforge.api.dto.BriefingRequestDTO;
import secureforge.api.model.ProjectBriefing;
import secureforge.api.service.ProjectBriefingService;

@RestController
@RequestMapping("/api/briefings")
public class BriefingController {

    private final ProjectBriefingService briefingService;

    public BriefingController(ProjectBriefingService briefingService) {
        this.briefingService = briefingService;
    }

    @PostMapping
    public ResponseEntity<ProjectBriefing> createBriefing(@Valid @RequestBody BriefingRequestDTO briefingDto) {
        ProjectBriefing newBriefing = briefingService.saveBriefing(briefingDto);
        return new ResponseEntity<>(newBriefing, HttpStatus.CREATED);
    }
}
