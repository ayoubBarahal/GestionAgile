package project.gestionprojet.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Repositories.ProjetRepo;
import project.gestionprojet.Service.ProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetServiceImpl implements ProjectService {

    @Autowired
    private ProjetRepo projetRepo;

    @Override
    public ProjetDTO addProjet(ProjetDTO projetDTO) {
        Projet projet = new Projet();
        projet.setIdProjet(projetDTO.getIdProjet());
        projet.setNomProjet(projetDTO.getNomProjet());
        Projet projetSaved = projetRepo.save(projet);
        if(projetSaved != null) {
            return projetDTO;
        }
        else {
            throw new IllegalStateException("Failed to add projet");
        }
    }

    @Override
    public ProjetDTO updateProjet(int id, ProjetDTO projet) {

            Optional<Projet> projetToUpdate = projetRepo.findById(id);
            if (projetToUpdate.isPresent()) {
                if(!Objects.equals(projet.getNomProjet(), projetToUpdate.get().getNomProjet())) {
                    projetToUpdate.get().setNomProjet(projet.getNomProjet());
                }
            }
            assert projetToUpdate.isPresent() :new IllegalStateException("projet n'est pas mis a jour correctement");
            Projet projetSaved = projetRepo.save(projetToUpdate.get());
            return new ProjetDTO(projetSaved.getIdProjet(), projetSaved.getNomProjet()) ;

    }

    @Override
    public ProjetDTO getProjet(int id) {
        Optional<Projet> projetOptional = projetRepo.findById(id);
        if (projetOptional.isEmpty()) {
            throw new IllegalStateException("le projet n'existe pas");
        }
        ProjetDTO projetDTO = new ProjetDTO(projetOptional.get().getIdProjet(), projetOptional.get().getNomProjet());
        return projetDTO;
    }

    @Override
    public List<ProjetDTO> getProjets() {
        List<Projet> projets = projetRepo.findAll();
        List<ProjetDTO> projetDTOs = new ArrayList<>();

        for (Projet projet : projets) {
            ProjetDTO projetDTO = new ProjetDTO();
            projetDTO.setIdProjet(projet.getIdProjet());
            projetDTO.setNomProjet(projet.getNomProjet());
            projetDTOs.add(projetDTO);
        }
        return projetDTOs;
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
    public ProjetDTO getProjetByName(String projetName) {
        Projet projet = projetRepo.findByNomProjet(projetName);
        if (projet == null) {
            throw new IllegalStateException("projet n'existe pas");
        }
        return new ProjetDTO(projet.getIdProjet(), projet.getNomProjet());
    }

}
