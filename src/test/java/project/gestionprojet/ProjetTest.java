package project.gestionprojet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.ServiceImpl.ProjetServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProjetTest {
    @Autowired
    private ProjetServiceImpl projetService;
    private static Projet projet;
    @BeforeAll
    public static void init() {
        projet=new Projet("gestion agile");
    }
    @Test
    public void ajouter() {
        Projet projetTest = projetService.addProjet(projet);
        assertNotNull(projetTest);
    }
}
