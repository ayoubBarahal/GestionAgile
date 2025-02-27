package project.gestionprojet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.gestionprojet.Entities.Projet;

import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Projet, Integer> {
    Projet save(Projet projet);
    Projet findByNomProjet(String projetName);
}
