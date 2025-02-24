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
   public Optional<ProductBacklog> findProductBacklog(@PathVariable String nom) {
        return productBacklogService.findProductBacklogByNom(nom) ;
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
