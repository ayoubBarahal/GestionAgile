package project.gestionprojet.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.ProductBacklog;

@Repository
public interface ProductBacklogRepo extends JpaRepository<ProductBacklog, Integer> {

    @Query("SELECT pb FROM ProductBacklog pb WHERE pb.nom = :nom")
    ProductBacklog findByNom(@Param("nom") String nom);
    ProductBacklog findById(int id);

}
