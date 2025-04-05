package project.gestionprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.ServiceImpl.ProjetServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjetTest {

    @Mock
    private ProjetServiceImpl projetService;

    @InjectMocks
    private ProjetTest testInstance;

    private ProjetDTO projet;

    @BeforeEach
    public void init() {
        projet = new ProjetDTO(1, "gestion agile");
    }

    @Test
    public void ajouter() {
        when(projetService.addProjet(projet)).thenReturn(projet);
        ProjetDTO projetTest = projetService.addProjet(projet);
        assertNotNull(projetTest);
    }

    @Test
    public void modifier() {
        ProjetDTO updated = new ProjetDTO(1, "projet updated");
        when(projetService.updateProjet(updated.getIdProjet(), updated)).thenReturn(updated);

        ProjetDTO projetTest = projetService.updateProjet(updated.getIdProjet(), updated);
        assertNotNull(projetTest);
    }

    @Test
    public void getProjet() {
        when(projetService.getProjet(projet.getIdProjet())).thenReturn(projet);

        ProjetDTO projetTest = projetService.getProjet(projet.getIdProjet());
        assertNotNull(projetTest, "ce projet n'existe pas");
    }

    @Test
    public void getProjets() {
        List<ProjetDTO> projets = Arrays.asList(projet);
        when(projetService.getProjets()).thenReturn(projets);

        List<ProjetDTO> projetsTest = projetService.getProjets();
        assertNotNull(projetsTest);
    }

    @Test
    public void getProjetByName() {
        when(projetService.getProjetByName(projet.getNomProjet())).thenReturn(projet);

        ProjetDTO projetTest = projetService.getProjetByName(projet.getNomProjet());
        assertNotNull(projetTest);
    }
}
