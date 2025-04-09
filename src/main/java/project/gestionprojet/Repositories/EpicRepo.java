package project.gestionprojet.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Entities.ProductBacklog;

import java.util.List;

@Repository
public interface EpicRepo extends JpaRepository<Epic, Integer> {
    List<Epic> findAllByProductBacklog(ProductBacklog productBacklog);


    @Modifying
    @Transactional
    @Query("DELETE FROM Epic e WHERE e.productBacklog.idProductBacklog = :productBacklogId")
    void deleteByProductBacklogId(@Param("productBacklogId") int productBacklogId);}
