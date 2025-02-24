package project.gestionprojet.ServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Repositories.ProductBacklogRepo;
import project.gestionprojet.Service.ProductBacklogService;

import java.util.Optional;

@Service
public class ProductBacklogServiceImpl implements ProductBacklogService {

    @Autowired
    ProductBacklogRepo productBacklogRepo;

    @Override
    public ProductBacklog addProductBacklog(ProductBacklog productBacklog) {
        return productBacklogRepo.save(productBacklog);
    }

    @Override
    public Optional<ProductBacklog> findProductBacklogByNom(String nom) {
        return Optional.ofNullable(productBacklogRepo.findByNom(nom));
    }

    @Override
    public ProductBacklog deleteProductBacklog(int idProductBacklog) {
        return productBacklogRepo.deleteById(idProductBacklog) ;
    }

    @Override
    public ProductBacklog updateProductBacklog(int id ,ProductBacklog productBacklog) {
        Optional<ProductBacklog> existingBacklogOptional = productBacklogRepo.findById(id);

        ProductBacklog existingBacklog = existingBacklogOptional.get();

        if (productBacklog.getIdProductBacklog() != 0) {
            existingBacklog.setIdProductBacklog(productBacklog.getIdProductBacklog());
        }

        if (productBacklog.getNom() != null ) {
            existingBacklog.setNom(productBacklog.getNom());
        }

        return productBacklogRepo.save(existingBacklog);
    }



}
