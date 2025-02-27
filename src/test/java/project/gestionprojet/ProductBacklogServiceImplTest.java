package project.gestionprojet;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Entities.Projet;
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
        ProductBacklog result = productBacklogService.findProductBacklogByNom(productBacklog.getNom());
        assertNotNull(result);
    }

@Test
@Transactional
@Rollback(false)
void remover() {
    // 1️⃣ Récupérer l'entité avant suppression
    ProductBacklog productBacklog1 = productBacklogService.findProductBacklogByNom(productBacklog.getNom());

    if (productBacklog1 != null) {
        // 2️⃣ Supprimer l'entité en passant l'ID
        productBacklogService.deleteProductBacklog(productBacklog1.getIdProductBacklog());

        // 3️⃣ Vérifier que l'entité a bien été supprimée en la recherchant à nouveau
        ProductBacklog deletedProductBacklog = productBacklogService.findProductBacklogByNom(productBacklog.getNom());

        // 4️⃣ Assertion pour s'assurer qu'elle n'existe plus
        assertNull(deletedProductBacklog, "L'entité ProductBacklog n'a pas été supprimée !");
    } else {
        fail("L'entité ProductBacklog à supprimer n'existe pas !");
    }
}


}
