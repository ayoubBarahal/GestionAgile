package project.gestionprojet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Entities.SprintBacklog;

@Repository
public interface SprintBacklogRepo extends JpaRepository<SprintBacklog, Integer> {

}
