package project.gestionprojet.Service;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Entities.Projet;

import java.util.List;


public interface ProjectService {
    ProjetDTO addProjet(ProjetDTO projetDTO);
    ProjetDTO updateProjet(int id, ProjetDTO projetDTO);
    ProjetDTO getProjet(int id);
   List<ProjetDTO> getProjets();
   void deleteProjet(int id);
   ProjetDTO getProjetByName(String projetName);
}
