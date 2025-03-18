package project.gestionprojet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.gestionprojet.Entities.SprintBacklog;

public interface SprintBacklogRepo extends JpaRepository<SprintBacklog, Integer> {
}
