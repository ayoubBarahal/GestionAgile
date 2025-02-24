package project.gestionprojet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Repositories.ProductBacklogRepo;
import project.gestionprojet.ServiceImpl.ProductBacklogServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductBacklogServiceImplTest {

    @Autowired
    private ProductBacklogServiceImpl productBacklogService;

    private static ProductBacklog productBacklog;

    @BeforeAll
    static void init(){
         productBacklog = new ProductBacklog("First Product Backlog ");
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
        Optional<ProductBacklog> result = productBacklogService.findProductBacklogByNom(productBacklog.getNom());
        assertNotNull(result);
    }

    @Test
    void remover() {
        ProductBacklog result = productBacklogService.deleteProductBacklog(productBacklog.getIdProductBacklog());
        assertNull(result);
    }

}
