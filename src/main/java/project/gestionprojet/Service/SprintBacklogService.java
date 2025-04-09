package project.gestionprojet.Service;

import project.gestionprojet.DTO.SprintBacklogDTO;
import project.gestionprojet.Entities.SprintBacklog;

import java.util.List;

public interface SprintBacklogService  {
    SprintBacklogDTO createSprintBacklog(SprintBacklogDTO sprintBacklog);
    SprintBacklogDTO getSprintBacklog(int id);
    List<SprintBacklogDTO> getSprintBacklogs();
    SprintBacklogDTO updateSprintBacklog(int id ,SprintBacklogDTO sprintBacklog);
    void deleteSprintBacklog(int id);
}
