package project.gestionprojet.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.DTO.SprintDTO;
import project.gestionprojet.Entities.Sprint;
import project.gestionprojet.Repositories.SprintRepo;
import project.gestionprojet.Service.SprintService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SprintServiceImpl implements SprintService {

    @Autowired
    private SprintRepo sprintRepo;

    @Override
    public SprintDTO addSprint(SprintDTO sprintDTO) {
        Sprint sprint = convertToSprint(sprintDTO);
        return convertToSprintDTO(sprintRepo.save(sprint));
    }

    @Override
    public SprintDTO updateSprint(SprintDTO sprintDTO) {
        return sprintRepo.findById(sprintDTO.getIdSprint())
                .map(existingSprint -> {
                    Sprint updatedSprint = convertToSprint(sprintDTO);
                    updatedSprint.setIdSprint(existingSprint.getIdSprint()); // conserver l'ID
                    return convertToSprintDTO(sprintRepo.save(updatedSprint));
                })
                .orElse(null);
    }

    @Override
    public SprintDTO deleteSprint(SprintDTO sprintDTO) {
        return sprintRepo.findById(sprintDTO.getIdSprint())
                .map(sprint -> {
                    sprintRepo.delete(sprint);
                    return convertToSprintDTO(sprint);
                })
                .orElse(null);
    }

    @Override
    public List<SprintDTO> getAllSprint() {
        return sprintRepo.findAll()
                .stream()
                .map(this::convertToSprintDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SprintDTO getSprintById(int id) {
        return sprintRepo.findById(id)
                .map(this::convertToSprintDTO)
                .orElse(null);
    }

    @Override
    public SprintDTO getSprintByName(String name) {
        Sprint sprint = sprintRepo.findByNomSprint(name);
        return sprint != null ? convertToSprintDTO(sprint) : null;
    }

    public SprintDTO convertToSprintDTO(Sprint sprint) {
        SprintDTO sprintDTO = new SprintDTO();
        sprintDTO.setIdSprint(sprint.getIdSprint());
        sprintDTO.setNomSprint(sprint.getNomSprint());
        sprintDTO.setDateDebut(sprint.getDateDebut());
        sprintDTO.setDateFin(sprint.getDateFin());
        sprintDTO.setIdSprintBacklog(sprint.getSprintBacklog().getIdSprintBacklog());
        return sprintDTO;
    }

    public Sprint convertToSprint(SprintDTO sprintDTO) {
        if (sprintDTO == null) return null;
        Sprint sprint = new Sprint();
        sprint.setIdSprint(sprintDTO.getIdSprint());
        sprint.setNomSprint(sprintDTO.getNomSprint());
        sprint.setDateDebut(sprintDTO.getDateDebut());
        sprint.setDateFin(sprintDTO.getDateFin());
        return sprint;
    }
}
