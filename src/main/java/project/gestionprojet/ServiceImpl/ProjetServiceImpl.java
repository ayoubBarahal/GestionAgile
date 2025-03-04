package project.gestionprojet.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Repositories.ProjetRepo;
import project.gestionprojet.Service.ProjectService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjetServiceImpl implements ProjectService {

    @Autowired
    private ProjetRepo projetRepo;

    @Override
    public Projet addProjet(ProjetDTO projetDTO) {
        Projet projet = new Projet();
        projet.setIdProjet(projetDTO.getIdProjet());
        projet.setNomProjet(projetDTO.getNomProjet());
        return projetRepo.save(projet);
    }

    @Override
    public Projet updateProjet(int id, ProjetDTO projet) {
        boolean exist = projetRepo.existsById(projet.getIdProjet());
        if (!exist) {
            throw new IllegalStateException("projet n'existe pas");
        }
        else {
            Optional<Projet> projetToUpdate = projetRepo.findById(id);
            if (projetToUpdate.isPresent()) {
                if(!Objects.equals(projet.getNomProjet(), projetToUpdate.get().getNomProjet())) {
                    projetToUpdate.get().setNomProjet(projet.getNomProjet());
                }
            }
            System.out.println("ach katkhawar a khay younesse");
            assert projetToUpdate.isPresent() :new IllegalStateException("projet n'est pas mis a jour correctement");
            return projetRepo.save(projetToUpdate.get());
        }
    }

    @Override
    public Projet getProjet(int id) {
        Optional<Projet> projetOptional = projetRepo.findById(id);
        if (projetOptional.isEmpty()) {
            throw new IllegalStateException("le projet n'existe pas");
        }
        return projetOptional.get();
    }

    @Override
    public List<Projet> getProjets() {
        return projetRepo.findAll();
    }

    @Override
    public void deleteProjet(int id) {
        Optional<Projet> projetToDelete = projetRepo.findById(id);
        if (projetToDelete.isPresent()) {
             projetRepo.deleteById(id);
        }
        else {
            throw new IllegalStateException("projet n'existe pas");
        }
    }

    @Override
    public Projet getProjetByName(String projetName) {
        Projet projet = projetRepo.findByNomProjet(projetName);
        if (projet == null) {
            throw new IllegalStateException("projet n'existe pas");
        }
        return projet;
    }

}
