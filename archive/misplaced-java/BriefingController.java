package secureforge.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import secureforge.dto.BriefingRequestDTO;
import secureforge.model.ProjectBriefing;
import secureforge.service.ProjectBriefingService;

@RestController
@RequestMapping("/api/briefings")
public class BriefingController {

    private final ProjectBriefingService briefingService;

    @Autowired
    public BriefingController(ProjectBriefingService briefingService) {
        this.briefingService = briefingService;
    }

    @PostMapping
    public ResponseEntity<ProjectBriefing> createBriefing(@Valid @RequestBody BriefingRequestDTO briefingDto) {
        try {
            ProjectBriefing newBriefing = briefingService.saveBriefing(briefingDto);
            return new ResponseEntity<>(newBriefing, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}