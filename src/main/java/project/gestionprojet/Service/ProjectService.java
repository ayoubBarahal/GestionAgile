package project.gestionprojet.Service;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Entities.Projet;

import java.util.List;


public interface ProjectService {
    Projet addProjet(ProjetDTO projetDTO);
    Projet updateProjet(int id, ProjetDTO projetDTO);
    Projet getProjet(int id);
   List<Projet> getProjets();
   void deleteProjet(int id);
   Projet getProjetByName(String projetName);
}
