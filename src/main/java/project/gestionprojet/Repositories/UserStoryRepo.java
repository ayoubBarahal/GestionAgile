package project.gestionprojet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.Priority;
import project.gestionprojet.Entities.Status;
import project.gestionprojet.Entities.UserStory;

import java.util.List;

@Repository
public interface UserStoryRepo extends JpaRepository<UserStory, Integer> {
    UserStory findByIdUserStory(int idUserStory);
    UserStory findByTitre(String Titre);
    List<UserStory> findByEpicIdEpic(int idEpic);
    List<UserStory> findByStatus(Status status);
    List<UserStory> findByPriority(Priority priority);
}
