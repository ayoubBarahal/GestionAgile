package project.gestionprojet.Service;

import project.gestionprojet.Entities.Task;
import project.gestionprojet.Entities.UserStory;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    Task updateTask(int id ,Task task);
    void deleteTask(Task task);
    List<Task> getTasks();
    List<Task> getTasksByUserStory(UserStory userStory);


}
