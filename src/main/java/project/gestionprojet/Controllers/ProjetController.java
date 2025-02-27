package project.gestionprojet.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Service.ProjectService;

@RestController
public class ProjetController {


    @Autowired
    private ProjectService projetService;

    @PostMapping("/api/addProjet")
    public Projet addProjet(@RequestBody Projet projet) {
        return projetService.addProjet(projet);
    }

}
