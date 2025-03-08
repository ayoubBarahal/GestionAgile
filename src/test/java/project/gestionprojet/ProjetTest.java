package project.gestionprojet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Service.ProjectService;
import project.gestionprojet.ServiceImpl.ProjetServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProjetTest {
    @Autowired
    private ProjectService projetService;

    private static ProjetDTO projet;

    @BeforeAll
    public static void init() {
        projet=new ProjetDTO(1,"gestion agile");
    }

    @Test
    public void ajouter() {
        ProjetDTO projetTest = projetService.addProjet(projet);
        assertNotNull(projetTest);
    }
    @Test
    public void modifier() {
        projet.setNomProjet("Projet Updated");
        ProjetDTO projetDTO=new ProjetDTO(projet.getIdProjet(),"projet updated");
        ProjetDTO projetTest = projetService.updateProjet(projetDTO.getIdProjet(),projetDTO);
        assertNotNull(projetTest);

    }

    @Test
    public void getProjet(){
        ProjetDTO projetTest = projetService.getProjet(projet.getIdProjet());
        assertNotNull(projetTest, "ce projet n'existe pas");
    }

    @Test
    public void getProjets(){
        List<ProjetDTO> projetsTest = projetService.getProjets();
        assertNotNull(projetsTest);
    }

    @Test
    public void getProjetByName(){
        ProjetDTO projetTest = projetService.getProjetByName(projet.getNomProjet());
        assertNotNull(projetTest);
    }
}
