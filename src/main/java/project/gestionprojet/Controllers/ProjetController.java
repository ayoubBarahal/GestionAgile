package project.gestionprojet.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Service.ProjetService;

@RestController
@RequestMapping("/api")
public class ProjetController {

    private static ProjetService projectService;
    @Autowired
    public ProjetController(ProjetService projectService) {
        ProjetController.projectService = projectService;
    }

    @PostMapping("/addprojet")
    public void addProjet(@RequestBody Projet projet) {
        projectService.addProjet(projet);
    }
}
