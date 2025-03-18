package project.gestionprojet.Service;

import project.gestionprojet.DTO.TaskDTO;
import project.gestionprojet.Entities.Status;
import project.gestionprojet.Entities.Task;
import project.gestionprojet.Entities.UserStory;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO task);
    TaskDTO updateTask(int idTask ,TaskDTO task);
    void deleteTask(int idTask);
    List<TaskDTO> getAllTasksByUserStory( int  idUserStory);
    Status getStatus(int idTask);
    Status updateStatus(int idTask , Status status);
}
