package project.gestionprojet.Service;

import project.gestionprojet.DTO.SprintBacklogDTO;

import java.util.List;

public interface SprintBacklogService {
    SprintBacklogDTO createSprintBacklog(SprintBacklogDTO sprintBacklog);
    SprintBacklogDTO getSprintBacklog(int id);
    List<SprintBacklogDTO> getSprintBacklogs();
    SprintBacklogDTO updateSprintBacklog(SprintBacklogDTO sprintBacklog);
    void deleteSprintBacklog(int id);
}
