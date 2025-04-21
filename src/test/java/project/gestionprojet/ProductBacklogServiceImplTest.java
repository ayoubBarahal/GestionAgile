package project.gestionprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import project.gestionprojet.DTO.ProductBacklogDTO;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Repositories.EpicRepo;
import project.gestionprojet.Repositories.ProductBacklogRepo;
import project.gestionprojet.Repositories.ProjetRepo;
import project.gestionprojet.ServiceImpl.ProductBacklogServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductBacklogServiceImplTest {

    @Mock
    private ProductBacklogRepo productBacklogRepo;

    @Mock
    private ProjetRepo projetRepo;

    @Mock
    private EpicRepo epicRepo;

    @InjectMocks
    private ProductBacklogServiceImpl productBacklogService;

    private ProductBacklogDTO productBacklogDTO;
    private ProductBacklog productBacklog;
    private Projet projet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        projet = new Projet();
        projet.setIdProjet(1);
        projet.setNomProjet("Test Project");

        productBacklog = new ProductBacklog();
        productBacklog.setIdProductBacklog(1);
        productBacklog.setNom("Test Product Backlog");
        productBacklog.setProjet(projet);

        productBacklogDTO = new ProductBacklogDTO(1, "Test Product Backlog", 1);
    }

    @Test
    void testAddProductBacklog_Success() {
        when(projetRepo.findById(anyInt())).thenReturn(Optional.of(projet));
        when(productBacklogRepo.save(any(ProductBacklog.class))).thenReturn(productBacklog);

        ProductBacklogDTO result = productBacklogService.addProductBacklog(productBacklogDTO);

        assertNotNull(result);
        assertEquals(productBacklogDTO.getIdProductBacklog(), result.getIdProductBacklog());
        assertEquals(productBacklogDTO.getNom(), result.getNom());
        assertEquals(productBacklogDTO.getIdProjet(), result.getIdProjet());

        verify(projetRepo).findById(productBacklogDTO.getIdProjet());
        verify(productBacklogRepo).save(any(ProductBacklog.class));
    }

    @Test
    void testAddProductBacklog_ProjetNotFound() {
        when(projetRepo.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productBacklogService.addProductBacklog(productBacklogDTO);
        });

        assertEquals("Projet non trouvé avec l'ID : " + productBacklogDTO.getIdProjet(), exception.getMessage());

        verify(projetRepo).findById(productBacklogDTO.getIdProjet());
        verify(productBacklogRepo, never()).save(any(ProductBacklog.class));
    }

    @Test
    void testFindProductBacklogByNom_Success() {
        when(productBacklogRepo.findByNom(anyString())).thenReturn(productBacklog);

        ProductBacklogDTO result = productBacklogService.findProductBacklogByNom("Test Product Backlog");

        assertNotNull(result);
        assertEquals(productBacklog.getIdProductBacklog(), result.getIdProductBacklog());
        assertEquals(productBacklog.getNom(), result.getNom());
        assertEquals(productBacklog.getProjet().getIdProjet(), result.getIdProjet());

        verify(productBacklogRepo).findByNom("Test Product Backlog");
    }

    @Test
    void testFindProductBacklogByNom_NotFound() {
        when(productBacklogRepo.findByNom(anyString())).thenReturn(null);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            productBacklogService.findProductBacklogByNom("Non-existent Backlog");
        });

        assertEquals("Product Backlog n'existe pas", exception.getMessage());

        verify(productBacklogRepo).findByNom("Non-existent Backlog");
    }

    @Test
    void testUpdateProductBacklog_Success() {
        when(productBacklogRepo.findById(anyInt())).thenReturn(Optional.of(productBacklog));
        when(productBacklogRepo.save(any(ProductBacklog.class))).thenReturn(productBacklog);

        ProductBacklogDTO updatedDTO = new ProductBacklogDTO(1, "Updated Product Backlog", 1);
        ProductBacklogDTO result = productBacklogService.updateProductBacklog(1, updatedDTO);

        assertNotNull(result);
        assertEquals(updatedDTO.getIdProductBacklog(), result.getIdProductBacklog());
        assertEquals(updatedDTO.getNom(), result.getNom());
        assertEquals(updatedDTO.getIdProjet(), result.getIdProjet());

        verify(productBacklogRepo).findById(1);
        verify(productBacklogRepo).save(any(ProductBacklog.class));
    }

    @Test
    void testUpdateProductBacklog_NotFound() {
        when(productBacklogRepo.findById(anyInt())).thenReturn(Optional.empty());

        ProductBacklogDTO updatedDTO = new ProductBacklogDTO(1, "Updated Product Backlog", 1);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            productBacklogService.updateProductBacklog(1, updatedDTO);
        });

        assertEquals("Le product backlog avec l'ID 1 n'existe pas", exception.getMessage());

        verify(productBacklogRepo).findById(1);
        verify(productBacklogRepo, never()).save(any(ProductBacklog.class));
    }

    @Test
    void testDeleteProductBacklog_Success() {
        when(productBacklogRepo.findById(anyInt())).thenReturn(Optional.of(productBacklog));
        doNothing().when(epicRepo).deleteByProductBacklogId(anyInt());
        doNothing().when(productBacklogRepo).deleteById(anyInt());

        productBacklogService.deleteProductBacklog(1);

        verify(epicRepo).deleteByProductBacklogId(1);
        verify(productBacklogRepo).findById(1);
        verify(productBacklogRepo).deleteById(1);
    }

    @Test
    void testDeleteProductBacklog_NotFound() {
        when(productBacklogRepo.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            productBacklogService.deleteProductBacklog(1);
        });

        assertEquals("Proudct Backlog n'existe pas", exception.getMessage());

        verify(epicRepo).deleteByProductBacklogId(1);
        verify(productBacklogRepo).findById(1);
        verify(productBacklogRepo, never()).deleteById(anyInt());
    }
}