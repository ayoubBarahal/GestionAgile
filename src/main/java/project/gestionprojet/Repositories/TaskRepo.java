package project.gestionprojet.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.Task;
import project.gestionprojet.Entities.UserStory;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {


    List<Task> findAllByUserStory(UserStory userStory);
}
