package project.gestionprojet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.gestionprojet.Entities.Projet;


public interface ProjetRepo extends JpaRepository<Projet, Integer> {
    Projet save(Projet projet);
}
