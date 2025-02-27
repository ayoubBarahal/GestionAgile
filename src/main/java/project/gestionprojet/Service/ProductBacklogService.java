package project.gestionprojet.Service;

import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Entities.ProductBacklog;

import java.util.List;


public interface ProductBacklogService {
    ProductBacklog addProductBacklog(ProductBacklog productBacklog) ;
    ProductBacklog findProductBacklogByNom(String nom) ;
    ProductBacklog deleteProductBacklog(int idProductBacklog) ;
    ProductBacklog updateProductBacklog(int id ,ProductBacklog productBacklog) ;

}
