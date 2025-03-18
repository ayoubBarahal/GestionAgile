package project.gestionprojet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.Projet;


@Repository
public interface ProjetRepo extends JpaRepository<Projet, Integer> {
    Projet findByNomProjet(String nomProjet);
}
