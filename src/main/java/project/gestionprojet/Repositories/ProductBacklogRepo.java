package project.gestionprojet.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.ProductBacklog;

@Repository
public interface ProductBacklogRepo extends JpaRepository<ProductBacklog, Integer> {
    ProductBacklog save(ProductBacklog productBacklog);
    ProductBacklog findByNom(String nom);
    ProductBacklog deleteById(int id);

    void findAllEpic();
}
