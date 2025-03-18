package project.gestionprojet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.Sprint;
@Repository
public interface SprintRepo extends JpaRepository<Sprint, Integer> {
    Sprint findByNomSprint(String name);
}
