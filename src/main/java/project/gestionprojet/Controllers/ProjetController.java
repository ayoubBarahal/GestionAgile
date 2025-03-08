package project.gestionprojet.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Service.ProjectService;

import java.util.List;

@RestController
public class ProjetController {


    @Autowired
    private ProjectService projectService;

    @PostMapping("/api/addProject")
    public ResponseEntity<ProjetDTO> addProjet(@RequestBody ProjetDTO projet) {
        ProjetDTO savedProjet = projectService.addProjet(projet);
        return ResponseEntity.ok().body(savedProjet);
    }

    @GetMapping("/api/getProject/{id}")
    public ResponseEntity<ProjetDTO> getProjetById(@PathVariable int id) {
        ProjetDTO projet=projectService.getProjet(id);
        return ResponseEntity.ok().body(projet);
    }

    @GetMapping("/api/getProject")
    public ResponseEntity<List<ProjetDTO>> getAllProjets() {
        List<ProjetDTO> projets = projectService.getProjets() ;
        return ResponseEntity.ok().body(projets);
    }

    @PutMapping("/api/updateProject/{id}")
    public ResponseEntity<ProjetDTO> updateProjet(@PathVariable int id, @RequestBody ProjetDTO projetDTO) {
        return ResponseEntity.ok(projectService.updateProjet(id, projetDTO));
    }

    @DeleteMapping("/api/deleteProject/{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable int id) {
        projectService.deleteProjet(id);
        return ResponseEntity.noContent().build();
    }


}
