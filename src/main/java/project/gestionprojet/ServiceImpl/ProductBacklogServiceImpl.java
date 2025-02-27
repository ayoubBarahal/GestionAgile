package project.gestionprojet.ServiceImpl;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Repositories.ProductBacklogRepo;
import project.gestionprojet.Service.ProductBacklogService;


import java.util.Optional;

@Service
@Transactional
public class ProductBacklogServiceImpl implements ProductBacklogService {

    @Autowired
    private ProductBacklogRepo productBacklogRepo;



    @Override
    public ProductBacklog addProductBacklog(ProductBacklog productBacklog) {
        return productBacklogRepo.save(productBacklog);
    }

    @Override
    public ProductBacklog findProductBacklogByNom(String nom){
        return productBacklogRepo.findByNom(nom);
    }

    @Override
    public ProductBacklog updateProductBacklog(int id ,ProductBacklog productBacklog) {

        Optional<ProductBacklog> existingBacklogOptional = productBacklogRepo.findById(id);
        ProductBacklog existingBacklog=null;

        if(existingBacklogOptional.isPresent()) {
             existingBacklog = existingBacklogOptional.get();

            if (productBacklog.getIdProductBacklog() != 0) {
                existingBacklog.setIdProductBacklog(productBacklog.getIdProductBacklog());
            }

            if (productBacklog.getNom() != null ) {
                existingBacklog.setNom(productBacklog.getNom());
            }}
        assert existingBacklog != null:new IllegalStateException("La product backlog est obligatoire");
        return productBacklogRepo.save(existingBacklog);
    }


    @Override
    public ProductBacklog deleteProductBacklog(int idProductBacklog) {
        return productBacklogRepo.deleteById(idProductBacklog) ;
    }



}
