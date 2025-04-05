package project.gestionprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.gestionprojet.DTO.ProductBacklogDTO;
import project.gestionprojet.DTO.ProjetDTO;

import project.gestionprojet.Service.ProductBacklogService;
import project.gestionprojet.Service.ProjectService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductBacklogServiceImplTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private ProductBacklogService productBacklogService;

    private ProductBacklogDTO productBacklog;
    private ProjetDTO projet;

    @BeforeEach
    void setUp() {
        projet = new ProjetDTO(1, "projet name");
        productBacklog = new ProductBacklogDTO(1, "First Product Backlog", projet.getIdProjet());
    }

    @Test
    void ajouter() {
        when(projectService.addProjet(projet)).thenReturn(projet);
        when(productBacklogService.addProductBacklog(productBacklog)).thenReturn(productBacklog);

        projet = projectService.addProjet(projet);
        ProductBacklogDTO result = productBacklogService.addProductBacklog(productBacklog);
        assertNotNull(result);
    }

    @Test
    void modifier() {
        ProductBacklogDTO updated = new ProductBacklogDTO(1, "Updated Name", 1);
        when(productBacklogService.updateProductBacklog(productBacklog.getIdProductBacklog(), updated)).thenReturn(updated);

        ProductBacklogDTO result = productBacklogService.updateProductBacklog(productBacklog.getIdProductBacklog(), updated);
        assertNotNull(result);
    }

    @Test
    void find() {
        when(productBacklogService.findProductBacklogByNom(productBacklog.getNom())).thenReturn(productBacklog);

        ProductBacklogDTO result = productBacklogService.findProductBacklogByNom(productBacklog.getNom());
        assertNotNull(result);
    }
}

