package project.gestionprojet.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gestionprojet.DTO.ProductBacklogDTO;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Service.ProductBacklogService;
import project.gestionprojet.ServiceImpl.ProductBacklogServiceImpl;

import java.util.Optional;

@RestController
public class ProductBacklogController {

    @Autowired
    private ProductBacklogService pBService;

    @PostMapping("/api/addProductBacklog")
    public ResponseEntity<ProductBacklogDTO> addProductBacklog(@RequestBody ProductBacklogDTO productBacklog) {
        ProductBacklogDTO productBacklogSaved = pBService.addProductBacklog(productBacklog);
        return ResponseEntity.ok(productBacklogSaved);
    }

   @GetMapping("/api/findProductBacklog/{nom}")
   public ResponseEntity<ProductBacklogDTO> findProductBacklog(@PathVariable String nom) {
        ProductBacklogDTO productBacklog = pBService.findProductBacklogByNom(nom);
        return ResponseEntity.ok(productBacklog);
   }


    @DeleteMapping("/api/deleteProductBacklog/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        pBService.deleteProductBacklog(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/updateProductBacklog/{id}")
    public ResponseEntity<ProductBacklogDTO> updateBacklog(@PathVariable int id, @RequestBody ProductBacklogDTO productBacklog) {
        ProductBacklogDTO updatedBacklog = pBService.updateProductBacklog(id, productBacklog);
        return ResponseEntity.ok(updatedBacklog);
    }


}
