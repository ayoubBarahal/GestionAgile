package project.gestionprojet.ServiceImpl;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.gestionprojet.DTO.ProductBacklogDTO;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Repositories.ProductBacklogRepo;
import project.gestionprojet.Repositories.ProjetRepo;
import project.gestionprojet.Service.ProductBacklogService;


import java.util.Optional;

@Service
@Transactional
public class ProductBacklogServiceImpl implements ProductBacklogService {

    @Autowired
    private ProductBacklogRepo productBacklogRepo;

    @Autowired
    private ProjetRepo projetRepo;


    @Override
    public ProductBacklogDTO addProductBacklog(ProductBacklogDTO productBacklogdto) {
        ProductBacklog productBacklog = new ProductBacklog();
        productBacklog.setIdProductBacklog(productBacklogdto.getIdProductBacklog());
        productBacklog.setNom(productBacklogdto.getNom());
        Optional<Projet> projetOpt = projetRepo.findById(productBacklogdto.getIdProjet());
        if (projetOpt.isPresent()) {
            productBacklog.setProjet(projetOpt.get());
        } else {
            throw new RuntimeException("Projet non trouvé avec l'ID : " + productBacklogdto.getIdProjet());
        }
        ProductBacklog savedProductBacklog = productBacklogRepo.save(productBacklog);
        return new ProductBacklogDTO(
                savedProductBacklog.getIdProductBacklog(),
                savedProductBacklog.getNom(),
                savedProductBacklog.getProjet().getIdProjet()
        );
    }

    @Override
    public ProductBacklogDTO findProductBacklogByNom(String nom) {
        ProductBacklog productBacklog = productBacklogRepo.findByNom(nom);
        if (productBacklog == null) {
            throw new IllegalStateException("Product Backlog n'existe pas");
        }
        int idProjet = (productBacklog.getProjet() != null) ? productBacklog.getProjet().getIdProjet() : null;
        return new ProductBacklogDTO(
                productBacklog.getIdProductBacklog(),
                productBacklog.getNom(),
                idProjet
        );
    }


    @Override
    public ProductBacklogDTO updateProductBacklog(int id, ProductBacklogDTO productBacklog) {
        Optional<ProductBacklog> existingBacklogOptional = Optional.ofNullable(productBacklogRepo.findByIdProductBacklog(id));

        if (existingBacklogOptional.isEmpty()) {
            throw new IllegalStateException("Le product backlog avec l'ID " + id + " n'existe pas");
        }

        ProductBacklog existingBacklog = existingBacklogOptional.get();
        existingBacklog.setNom(productBacklog.getNom());

        ProductBacklog savedProductBacklog = productBacklogRepo.save(existingBacklog);

        return new ProductBacklogDTO(
                savedProductBacklog.getIdProductBacklog(),
                savedProductBacklog.getNom(),
                savedProductBacklog.getProjet().getIdProjet()
        );
    }



    @Override
    public void deleteProductBacklog(int idProductBacklog) {
        Optional<ProductBacklog> productBacklogToDelete = Optional.ofNullable(productBacklogRepo.findByIdProductBacklog(idProductBacklog));
        if (productBacklogToDelete.isPresent()) {
            productBacklogRepo.deleteById(idProductBacklog);
        }
        else {
            throw new IllegalStateException("Proudct Backlog n'existe pas");
        }
    }



}
