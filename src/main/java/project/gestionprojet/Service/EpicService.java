package project.gestionprojet.Service;

import project.gestionprojet.DTO.EpicDTO;
import project.gestionprojet.Entities.Epic;

import java.util.List;

public interface EpicService {
    EpicDTO createEpic(EpicDTO epic);
    EpicDTO updateEpic(int idEpic , EpicDTO epic);
    void deleteEpic(int idEpic);
    List<EpicDTO> getAllEpics();
    List<EpicDTO> findAllEpicByProductBacklog(int idProductBacklog);
}
