package project.gestionprojet.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Repositories.ProjectRepo;
import project.gestionprojet.Service.ProjectService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjetServiceImpl implements ProjectService{

    private static ProjectRepo projectRepo;

    @Autowired
    public ProjetServiceImpl(ProjectRepo projectRepo) {
        ProjetServiceImpl.projectRepo = projectRepo;
    }

    @Override
    public Projet addProjet(Projet projet) {
       try{ Optional<Projet> projetOptional = projectRepo.findById(projet.getIdProjet());
        if (projetOptional.isPresent()) {
            throw new IllegalStateException("projet existe deja");
        }
       }catch (NullPointerException e) {
           e.printStackTrace();
       }
        return projectRepo.save(projet);
    }

    @Override
    public Projet updateProjet(int id, ProjetDTO projet) {
        boolean exist = projectRepo.existsById(projet.getIdProjet());
        if (!exist) {
            throw new IllegalStateException("projet n'existe pas");
        }
        else {
            Optional<Projet> projetOptional = projectRepo.findById(id);
            Projet projetToUpdate = null;
            if (projetOptional.isPresent()) {
                projetToUpdate = projetOptional.get();
                if(!Objects.equals(projet.getNomProjet(), projetToUpdate.getNomProjet())) {
                    projetToUpdate.setNomProjet(projet.getNomProjet());
                }
            }
            assert projetToUpdate != null:new IllegalStateException("projet n'est pas mis a jour correctement");
            return projectRepo.save(projetToUpdate);

        }
    }

    @Override
    public Projet getProjet(int id) {
        Optional<Projet> projetOptional = projectRepo.findById(id);
        if (projetOptional.isEmpty()) {
            throw new IllegalStateException("le projet n'existe pas");
        }
        return projetOptional.get();
    }

    @Override
    public List<Projet> getProjets() {
        return projectRepo.findAll();
    }

    @Override
    public Projet deleteProjet(int id) {
        return null;
    }

    @Override
    public Projet getProjetByName(String projetName) {
        Projet projet = projectRepo.findByNomProjet(projetName);
        if (projet == null) {
            throw new IllegalStateException("projet n'existe pas");
        }
       return projectRepo.findByNomProjet(projetName);
    }
}
