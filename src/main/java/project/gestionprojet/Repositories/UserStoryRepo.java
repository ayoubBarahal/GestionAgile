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
    @Query("SELECT u from UserStory u where u.epic.idEpic=:idEpic")
    List<UserStory> findByEpicIdEpic(@Param("idEpic")int idEpic);
    @Query("SELECT u from UserStory u where u.epic IS NOT NULL AND u.status=:status")
    List<UserStory> findByStatus(@Param("status") Status status);
    @Query("SELECT u from UserStory u where u.epic IS NOT NULL AND u.priority=:priority")
    List<UserStory> findByPriority(@Param("priority") Priority priority);
}
