package project.gestionprojet.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gestionprojet.DTO.SprintBacklogDTO;
import project.gestionprojet.Service.SprintBacklogService;

import java.util.List;

@RestController
@RequestMapping("/api/sprint")
public class SprintBacklogController {

    @Autowired
    private SprintBacklogService sprintBacklogService;

    @PostMapping("/addSprintBacklog")
    public ResponseEntity<SprintBacklogDTO> createSprintBacklog(@RequestBody SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklogDTO sprintBacklog = sprintBacklogService.createSprintBacklog(sprintBacklogDTO);
        return ResponseEntity.ok().body(sprintBacklog) ;
    }

    @GetMapping("/getAllSprintBacklog")
    public List<SprintBacklogDTO> getSprintBacklogs() {
        return sprintBacklogService.getSprintBacklogs();
    }

    @GetMapping("/getSprintById/{id}")
    public SprintBacklogDTO getSprintBacklogById(@PathVariable int id) {
        return sprintBacklogService.getSprintBacklog(id);
    }

    @PutMapping("/updateSprintBacklog")
    public SprintBacklogDTO updateSprintBacklog(@RequestBody SprintBacklogDTO sprintBacklogDTO) {
        return sprintBacklogService.updateSprintBacklog(sprintBacklogDTO);
    }

    @DeleteMapping("/deleteSprintBacklog/{id}")
    public String deleteSprintBacklog(@PathVariable int id) {
        sprintBacklogService.deleteSprintBacklog(id);
        return "Sprint Backlog avec ID " + id + " supprimé avec succès.";
    }

}

