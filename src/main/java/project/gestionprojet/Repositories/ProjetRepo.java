package project.gestionprojet.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.Projet;

import java.util.List;

@Repository
public interface ProjetRepo extends JpaRepository<Projet, Integer> {


    void deleteById(int projetId);
    Projet findByNomProjet(String nomProjet);
}
