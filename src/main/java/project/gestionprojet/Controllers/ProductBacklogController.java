package project.gestionprojet.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Service.ProductBacklogService;
import project.gestionprojet.ServiceImpl.ProductBacklogServiceImpl;

import java.util.Optional;

@RestController
public class ProductBacklogController {

    @Autowired
    private ProductBacklogService productBacklogService;

    @PostMapping("/api/addProductBacklog")
    public ProductBacklog addProductBacklog(@RequestBody ProductBacklog productBacklog) {
        return productBacklogService.addProductBacklog(productBacklog);
    }

   @GetMapping("/api/findProductBacklog/{nom}")
   public ProductBacklog findProductBacklog(@PathVariable String nom) {
        ProductBacklog productBacklog = productBacklogService.findProductBacklogByNom(nom);
        if (productBacklog==null) {
            throw new IllegalStateException("le productBacklog n'existe pas");
        }else{
            return productBacklog;
        }
   }


    @DeleteMapping("/api/deleteProductBacklog/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        if(productBacklogService.deleteProductBacklog(id)!=null) {
            return ResponseEntity.ok("Product deleted successfully");
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/api/updateProductBacklog/{id}")
    public ResponseEntity<ProductBacklog> updateBacklog(@PathVariable int id, @RequestBody ProductBacklog productBacklog) {
        ProductBacklog updatedBacklog = productBacklogService.updateProductBacklog(id, productBacklog);
        return ResponseEntity.ok(updatedBacklog);
    }

}
