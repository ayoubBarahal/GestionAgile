package project.gestionprojet.Service;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Entities.Projet;

import java.util.List;


public interface ProjectService {
    Projet addProjet(Projet projet);
    Projet updateProjet(int id, ProjetDTO projet);
    Projet getProjet(int id);
   List<Projet> getProjets();
  Projet deleteProjet(int id);
   Projet getProjetByName(String projetName);


}
