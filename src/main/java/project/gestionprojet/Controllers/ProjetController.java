package project.gestionprojet.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.gestionprojet.DTO.ProjetDTO;
=======
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> 365d226 (la classe projet done avec les tests RAS)
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Service.ProjetService;

@RestController
<<<<<<< HEAD
@RequestMapping("/api/projet")
=======
@RequestMapping("/api")
>>>>>>> 365d226 (la classe projet done avec les tests RAS)
public class ProjetController {

    private static ProjetService projectService;
    @Autowired
<<<<<<< HEAD
    private ProjectService projectService;

    @PostMapping("/addProject")
    public ResponseEntity<ProjetDTO> addProjet(@RequestBody ProjetDTO projet) {
        ProjetDTO savedProjet = projectService.addProjet(projet);
        return ResponseEntity.ok().body(savedProjet);
    }

    @GetMapping("/getProject/{id}")
    public ResponseEntity<ProjetDTO> getProjetById(@PathVariable int id) {
        ProjetDTO projet=projectService.getProjet(id);
        return ResponseEntity.ok().body(projet);
    }

    @GetMapping("/getProject")
    public ResponseEntity<List<ProjetDTO>> getAllProjets() {
        List<ProjetDTO> projets = projectService.getProjets() ;
        return ResponseEntity.ok().body(projets);
    }

    @PutMapping("/updateProject/{id}")
    public ResponseEntity<ProjetDTO> updateProjet(@PathVariable int id, @RequestBody ProjetDTO projetDTO) {
        return ResponseEntity.ok(projectService.updateProjet(id, projetDTO));
    }

    @DeleteMapping("/deleteProject/{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable int id) {
        projectService.deleteProjet(id);
        return ResponseEntity.noContent().build();
    }


=======
    public ProjetController(ProjetService projectService) {
        ProjetController.projectService = projectService;
    }

    @PostMapping("/addprojet")
    public void addProjet(@RequestBody Projet projet) {
        projectService.addProjet(projet);
    }
>>>>>>> 365d226 (la classe projet done avec les tests RAS)
}
