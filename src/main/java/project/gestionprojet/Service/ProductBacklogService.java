package project.gestionprojet.Service;

import project.gestionprojet.Entities.ProductBacklog;

import java.util.Optional;

public interface ProductBacklogService {
    ProductBacklog addProductBacklog(ProductBacklog productBacklog) ;
    Optional<ProductBacklog> findProductBacklogByNom(String nom) ;
    ProductBacklog deleteProductBacklog(int idProductBacklog) ;
    ProductBacklog updateProductBacklog(int id ,ProductBacklog productBacklog) ;
}
