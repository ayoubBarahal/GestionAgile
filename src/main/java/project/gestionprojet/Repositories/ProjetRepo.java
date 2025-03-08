package project.gestionprojet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.Projet;

import java.util.List;

@Repository
public interface ProjetRepo extends JpaRepository<Projet, Integer> {
    Projet findByNomProjet(String nomProjet);
}
