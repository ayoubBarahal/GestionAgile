package project.gestionprojet.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Service.ProjectService;

@RestController
public class ProjetController {


    @Autowired
    private ProjectService projetService;

    @PostMapping("/api/addProjet")
    public ResponseEntity<Projet> addProjet(@RequestBody ProjetDTO projet) {
        Projet savedProjet = projetService.addProjet(projet);
        return ResponseEntity.ok().body(savedProjet);
    }


}
