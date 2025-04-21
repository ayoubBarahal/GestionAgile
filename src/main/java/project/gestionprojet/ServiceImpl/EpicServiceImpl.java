package project.gestionprojet.ServiceImpl;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.DTO.EpicDTO;
import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Repositories.EpicRepo;
import project.gestionprojet.Repositories.ProductBacklogRepo;
import project.gestionprojet.Repositories.SprintBacklogRepo;
import project.gestionprojet.Service.EpicService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EpicServiceImpl implements EpicService {
    @Autowired
    private EpicRepo epicRepo;
    @Autowired
    private ProductBacklogRepo productBacklogRepo;
    @Autowired
    private SprintBacklogRepo sprintBacklogRepo;

    @Override
    public EpicDTO createEpic(EpicDTO ep) {
        if (ep == null) {
            throw new EntityNotFoundException("Epic is null");
        }
        Epic epic = new Epic();
        System.out.println("ID Product Backlog: " + ep.getIdProductBacklog());
        ProductBacklog productBacklog = productBacklogRepo.findById(ep.getIdProductBacklog()).orElse(null);
        System.out.println("Product Backlog: " + productBacklog.getIdProductBacklog());
        if (productBacklog == null) {
            throw new EntityNotFoundException("Product Backlog not found");
        }
        epic.setProductBacklog(productBacklog);
        epic.setTitre(ep.getTitre());
        epic.setDescription(ep.getDescription());
        epic.setSprintBacklogs(sprintBacklogRepo.findById(ep.getIdSprintBacklog()).orElse(null));
        Epic epicSaved=epicRepo.save(epic);
        ep.setIdEpic(epicSaved.getIdEpic());
        return ep;
    }

    @Override
    public EpicDTO updateEpic(int id, EpicDTO ep) {

        Optional<Epic> epic = epicRepo.findById(id);
        if (epic.isPresent()) {
            Epic epicToUpdate = epic.get();
            epicToUpdate.setTitre(ep.getTitre());
            epicToUpdate.setDescription(ep.getDescription());
            epicToUpdate.setProductBacklog(productBacklogRepo.findById(ep.getIdEpic()).orElse(null));
            epicToUpdate.setSprintBacklogs(sprintBacklogRepo.findById(ep.getIdSprintBacklog()).orElse(null));
            epicRepo.save(epicToUpdate);
            ep.setIdEpic(id);
            return ep;
        }
        throw new EntityNotFoundException("Epic not found");
    }

    @Override
    public void deleteEpic(int ep) {
        Optional<Epic> epicToDelete = epicRepo.findById(ep);
        if (epicToDelete.isPresent()) {
            epicRepo.delete(epicToDelete.get());
        }else {
            throw new EntityNotFoundException("Epic a supprimer est introuvable");
        }
    }

    @Override
    public List<EpicDTO> findAllEpicByProductBacklog(int intProductBacklog) {
        List<Epic> epicDTOs = epicRepo.findAllByProductBacklog(productBacklogRepo.findByIdProductBacklog(intProductBacklog).orElse(null));

        return convertToListDto(epicDTOs);
    }

    @Override
    public List<EpicDTO> getAllEpics() {
        List<Epic> epics = epicRepo.findAll();
        return convertToListDto(epics);
    }

    public List<EpicDTO> convertToListDto(List<Epic> epics) {
        List<EpicDTO> epicDTOsDTO = new ArrayList<>();
        for (Epic epic : epics) {
            EpicDTO epicDTO = new EpicDTO();
            epicDTO.setIdEpic(epic.getIdEpic());
            epicDTO.setTitre(epic.getTitre());
            epicDTO.setDescription(epic.getDescription());
            epicDTO.setIdEpic(epic.getIdEpic());
            epicDTO.setIdProductBacklog(productBacklogRepo.findById(epic.getProductBacklog().getIdProductBacklog()).orElse(null).getIdProductBacklog());
            epicDTO.setIdSprintBacklog(sprintBacklogRepo.findById(epic.getSprintBacklogs().getIdSprintBacklog()).orElse(null).getIdSprintBacklog());
            epicDTOsDTO.add(epicDTO);
        }
        return epicDTOsDTO;
    }
}