package project.gestionprojet;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import project.gestionprojet.DTO.ProductBacklogDTO;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Service.ProductBacklogService;
import project.gestionprojet.Service.ProjectService;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductBacklogServiceImplTest {

    @Autowired
    private ProductBacklogService productBacklogService;
    @Autowired
    private    ProjectService projectService ;

    private  ProductBacklogDTO productBacklog;

    private  ProjetDTO projet;

    @BeforeEach
    public  void init(){
        projet =new ProjetDTO(1,"projet name");
        projet =projectService.addProjet(projet)  ;
         productBacklog = new ProductBacklogDTO(1,"First Product Backlog ",projet.getIdProjet());
    }

    @Test
    void ajouter() {
        ProductBacklogDTO result = productBacklogService.addProductBacklog(productBacklog);
        assertNotNull(result);
    }


    @Test
    void modifier() {
        ProductBacklogDTO productBacklogDTOTest = new ProductBacklogDTO(1,"Updated Name",1);
        ProductBacklogDTO result = productBacklogService.updateProductBacklog(productBacklog.getIdProductBacklog(),productBacklogDTOTest);
        assertNotNull(result);
    }

    @Test
    void find(){
        // Ajouter un backlog avant de le chercher
        productBacklogService.addProductBacklog(productBacklog);

        ProductBacklogDTO result = productBacklogService.findProductBacklogByNom(productBacklog.getNom());
        assertNotNull(result);
    }




}
