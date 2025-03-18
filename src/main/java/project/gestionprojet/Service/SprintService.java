package project.gestionprojet.Service;

import project.gestionprojet.DTO.SprintDTO;

import java.util.List;

public interface SprintService {

    SprintDTO addSprint(SprintDTO sprint);
    SprintDTO updateSprint(SprintDTO sprint);
    SprintDTO deleteSprint(SprintDTO sprint);
    List<SprintDTO> getAllSprint();
    SprintDTO getSprintById(int id);
    SprintDTO getSprintByName(String name);

}
