package project.gestionprojet.Repositories;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.ProductBacklog;

import java.util.Optional;

@Repository
public interface ProductBacklogRepo extends JpaRepository<ProductBacklog, Integer> {

    @Query("SELECT pb FROM ProductBacklog pb WHERE pb.nom = :nom")
    ProductBacklog findByNom(@Param("nom") String nom);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductBacklog p WHERE p.projet.idProjet = :projetId")
    void deleteByProjetId(@Param("projetId") Integer projetId);

    Optional<ProductBacklog> findByIdProductBacklog(int idProductBacklog);
}
