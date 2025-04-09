package project.gestionprojet.ServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.DTO.EpicDTO;
import project.gestionprojet.DTO.SprintBacklogDTO;
import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Entities.SprintBacklog;
import project.gestionprojet.Repositories.SprintBacklogRepo;
import project.gestionprojet.Service.SprintBacklogService;

import java.util.ArrayList;
import java.util.List;
@Service
public class SprintBacklogServiceImpl implements SprintBacklogService {

    @Autowired
    private SprintBacklogRepo sprintBacklogRepo;


    @Override
    public SprintBacklogDTO createSprintBacklog(SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklog sprintBacklog = new SprintBacklog();
        sprintBacklog.setNom(sprintBacklogDTO.getNom());
        sprintBacklog.setDescription(sprintBacklogDTO.getDescription());
        sprintBacklogRepo.save(sprintBacklog) ;
        sprintBacklogDTO.setIdSprintBacklog(sprintBacklog.getIdSprintBacklog());
        return sprintBacklogDTO;
    }

    @Override
    public SprintBacklogDTO getSprintBacklog(int id) {
        SprintBacklog sprintBacklog = sprintBacklogRepo.findById(id).get();
        return convertToDto(sprintBacklog);
    }

    @Override
    public List<SprintBacklogDTO> getSprintBacklogs() {
        List<SprintBacklog> sprintBacklogs = sprintBacklogRepo.findAll();
        return convertToDto(sprintBacklogs);
    }

    @Override
    public SprintBacklogDTO updateSprintBacklog(int id ,SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklog sprintBacklog=sprintBacklogRepo.findById(id).get() ;
        if (sprintBacklog==null) {
            throw new EntityNotFoundException();
        }
        sprintBacklog.setNom(sprintBacklogDTO.getNom());
        sprintBacklog.setDescription(sprintBacklogDTO.getDescription());
       SprintBacklog sprintUpdated = sprintBacklogRepo.save(sprintBacklog);
        return convertToDto(sprintUpdated);
    }

    @Override
    public void deleteSprintBacklog(int id) {
        SprintBacklog sprintBacklog = sprintBacklogRepo.findById(id).get();
        sprintBacklogRepo.delete(sprintBacklog);
    }

    public SprintBacklogDTO convertToDto(SprintBacklog sprintBacklog) {
        SprintBacklogDTO sprintBacklogDTO = new SprintBacklogDTO();
        sprintBacklogDTO.setNom(sprintBacklog.getNom());
        sprintBacklogDTO.setDescription(sprintBacklog.getDescription());
        sprintBacklogDTO.setIdSprintBacklog(sprintBacklog.getIdSprintBacklog());
        return sprintBacklogDTO ;
    }

    public SprintBacklog convertToEntity(SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklog sprintBacklog = new SprintBacklog();
        sprintBacklog.setNom(sprintBacklogDTO.getNom());
        sprintBacklog.setDescription(sprintBacklogDTO.getDescription());
        return sprintBacklog;
    }

    public List<SprintBacklogDTO> convertToDto(List<SprintBacklog> sprintBacklogs) {
        List<SprintBacklogDTO> sprintBacklogDTOs = new ArrayList<>();
        for (SprintBacklog sprintBacklog : sprintBacklogs) {
            sprintBacklogDTOs.add(convertToDto(sprintBacklog));
        }
        return sprintBacklogDTOs;
    }




}
