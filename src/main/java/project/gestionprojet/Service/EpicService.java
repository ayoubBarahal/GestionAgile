package project.gestionprojet.Service;

import project.gestionprojet.DTO.EpicDTO;
import project.gestionprojet.DTO.ProductBacklogDTO;

import java.util.List;

public interface EpicService {
    EpicDTO createEpic(EpicDTO ep);
    EpicDTO updateEpic(int id,EpicDTO ep);
    void deleteEpic(int id);
    List<EpicDTO> findAllEpicByProductBacklog(int idProductBacklog);
    List<EpicDTO> getAllEpics();

}
