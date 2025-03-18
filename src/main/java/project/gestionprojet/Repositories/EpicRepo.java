package project.gestionprojet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Entities.ProductBacklog;

import java.util.List;

public interface EpicRepo extends JpaRepository<Epic, Integer> {
    List<Epic> findAllByProductBacklog(ProductBacklog productBacklog);
}
