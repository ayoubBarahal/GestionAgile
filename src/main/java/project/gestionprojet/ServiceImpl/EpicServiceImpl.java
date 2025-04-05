package project.gestionprojet.ServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.DTO.EpicDTO;
import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Repositories.EpicRepo;
import project.gestionprojet.Repositories.ProductBacklogRepo;
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

    @Override
    public EpicDTO createEpic(EpicDTO ep) {
        if (ep == null) {
            throw new EntityNotFoundException("Epic is null");
        }
        Epic epic = new Epic();
        epic.setProductBacklog(productBacklogRepo.findByIdProductBacklog(ep.getIdProductBacklog()));
        epic.setTitre(ep.getTitre());
        epic.setDescription(ep.getDescription());
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
                epicToUpdate.setProductBacklog(productBacklogRepo.findByIdProductBacklog(ep.getIdEpic()));
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
        List<Epic> epicDTOs = epicRepo.findAllByProductBacklog(productBacklogRepo.findByIdProductBacklog(intProductBacklog));

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
            epicDTO.setTitre(epic.getTitre());
            epicDTO.setDescription(epic.getDescription());
            epicDTO.setIdEpic(epic.getIdEpic());
            epicDTOsDTO.add(epicDTO);
        }
        return epicDTOsDTO;
    }
}
