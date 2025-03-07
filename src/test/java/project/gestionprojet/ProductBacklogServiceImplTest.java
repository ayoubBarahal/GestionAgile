package project.gestionprojet;

<<<<<<< HEAD

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import project.gestionprojet.DTO.ProductBacklogDTO;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Service.ProductBacklogService;
import project.gestionprojet.Service.ProjectService;
=======
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import project.gestionprojet.DTO.ProductBacklogDTO;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Repositories.ProductBacklogRepo;
import project.gestionprojet.Service.ProductBacklogService;
import project.gestionprojet.ServiceImpl.ProductBacklogServiceImpl;
>>>>>>> 365d226 (la classe projet done avec les tests RAS)


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductBacklogServiceImplTest {

    @Autowired
    private ProductBacklogService productBacklogService;

    private static ProductBacklogDTO productBacklog;

    private static ProjetDTO projet;

    @BeforeAll
    static void init(){
        projet =new ProjetDTO(1,"projet name");
         productBacklog = new ProductBacklogDTO(1,"First Product Backlog ",projet);
    }

    @Test
    void ajouter() {
        ProductBacklog result = productBacklogService.addProductBacklog(productBacklog);
        assertNotNull(result);
    }
    @Test
    void modifier() {
        productBacklog.setNom("Name Updates") ;
        ProductBacklog result = productBacklogService.updateProductBacklog(productBacklog.getIdProductBacklog(),productBacklog);
        assertNotNull(result);
    }

    @Test
    void find(){
        ProductBacklog result = productBacklogService.findProductBacklogByNom(productBacklog.getNom());
        assertNotNull(result);
    }

    @Test
    void remover() {
        ProductBacklog result = productBacklogService.deleteProductBacklog(productBacklog.getIdProductBacklog());
        assertNull(result);
    }

}
