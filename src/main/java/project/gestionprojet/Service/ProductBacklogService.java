package project.gestionprojet.Service;

import project.gestionprojet.DTO.ProductBacklogDTO;
import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Entities.ProductBacklog;

import java.util.List;


public interface ProductBacklogService {
    ProductBacklogDTO addProductBacklog(ProductBacklogDTO productBacklogDTO) ;
    ProductBacklogDTO findProductBacklogByNom(String nom) ;
    void deleteProductBacklog(int idProductBacklog) ;
    ProductBacklogDTO updateProductBacklog(int id ,ProductBacklogDTO productBacklogDTO) ;

}
