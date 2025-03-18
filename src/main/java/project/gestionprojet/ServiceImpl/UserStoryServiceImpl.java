package project.gestionprojet.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.DTO.UserStoryDTO;
import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Entities.Priority;
import project.gestionprojet.Entities.Status;
import project.gestionprojet.Entities.UserStory;
import project.gestionprojet.Repositories.EpicRepo;
import project.gestionprojet.Repositories.UserStoryRepo;
import project.gestionprojet.Service.TaskService;
import project.gestionprojet.Service.UserStoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStoryServiceImpl implements UserStoryService {

    @Autowired
    private UserStoryRepo userStoryRepo;

    @Autowired
    private EpicRepo epicRepo;

    public UserStoryDTO convertToUserStoryDTO(UserStory userStory) {
        if (userStory == null) return null;

        UserStoryDTO userStoryDTO = new UserStoryDTO();
        userStoryDTO.setIdUserStory(userStory.getIdUserStory());
        userStoryDTO.setTitre(userStory.getTitre());
        userStoryDTO.setDescription(userStory.getDescription());
        userStoryDTO.setDesire(userStory.getDesire());
        userStoryDTO.setGoal(userStory.getGoal());
        userStoryDTO.setRole(userStory.getRole());
        userStoryDTO.setPriority(userStory.getPriority());
        userStoryDTO.setStatus(userStory.getStatus());

        if (userStory.getEpic() != null) {
            userStoryDTO.setIdEpic(userStory.getEpic().getIdEpic());
        }

        return userStoryDTO;
    }

    public UserStory convertToUserStory(UserStoryDTO userStoryDTO) {
        if (userStoryDTO == null) return null;

        UserStory userStory = new UserStory();
        userStory.setIdUserStory(userStoryDTO.getIdUserStory());
        userStory.setTitre(userStoryDTO.getTitre());
        userStory.setDescription(userStoryDTO.getDescription());
        userStory.setDesire(userStoryDTO.getDesire());
        userStory.setGoal(userStoryDTO.getGoal());
        userStory.setRole(userStoryDTO.getRole());
        userStory.setPriority(userStoryDTO.getPriority());
        userStory.setStatus(userStoryDTO.getStatus());

        return userStory;
    }

    @Override
    public UserStoryDTO createUserStory(UserStoryDTO userStoryDTO) {
        UserStory userStory = convertToUserStory(userStoryDTO);

        // Récupérer et associer l'Epic si un ID est fourni
        if (userStoryDTO.getIdEpic() > 0) {
            Epic epic = epicRepo.findById(userStoryDTO.getIdEpic()).orElse(null);
            if (epic != null) {
                userStory.setEpic(epic);
            }
        }

        UserStory savedUserStory = userStoryRepo.save(userStory);
        return convertToUserStoryDTO(savedUserStory);
    }

    @Override
    public UserStoryDTO updateUserStory(UserStoryDTO userStoryDTO) {
        return userStoryRepo.findById(userStoryDTO.getIdUserStory())
                .map(existingUserStory -> {
                    UserStory updatedUserStory = convertToUserStory(userStoryDTO);
                    updatedUserStory.setIdUserStory(existingUserStory.getIdUserStory());

                    // Récupérer et associer l'Epic si un ID est fourni
                    if (userStoryDTO.getIdEpic() > 0) {
                        Epic epic = epicRepo.findById(userStoryDTO.getIdEpic()).orElse(null);
                        if (epic != null) {
                            updatedUserStory.setEpic(epic);
                        }
                    }

                    return convertToUserStoryDTO(userStoryRepo.save(updatedUserStory));
                })
                .orElse(null);
    }

    @Override
    public UserStoryDTO deleteUserStory(UserStoryDTO userStoryDTO) {
        return userStoryRepo.findById(userStoryDTO.getIdUserStory())
                .map(userStory -> {
                    userStoryRepo.delete(userStory);
                    return convertToUserStoryDTO(userStory);
                })
                .orElse(null);
    }

    @Override
    public UserStoryDTO getUserStoryById(int id) {
        return userStoryRepo.findById(id)
                .map(this::convertToUserStoryDTO)
                .orElse(null);
    }

    @Override
    public UserStoryDTO getUserStoryByUserName(String userName) {
        UserStory userStory = userStoryRepo.findByTitre(userName);
        return userStory != null ? convertToUserStoryDTO(userStory) : null;
    }

    // Méthode additionnelle pour récupérer toutes les user stories
    public List<UserStoryDTO> getAllUserStories() {
        return userStoryRepo.findAll()
                .stream()
                .map(this::convertToUserStoryDTO)
                .collect(Collectors.toList());
    }

    // Méthode pour récupérer les user stories par Epic
    public List<UserStoryDTO> getUserStoriesByEpicId(int epicId) {
        return userStoryRepo.findByEpicIdEpic(epicId)
                .stream()
                .map(this::convertToUserStoryDTO)
                .collect(Collectors.toList());
    }

    // Méthode pour récupérer les user stories par statut
    public List<UserStoryDTO> getUserStoriesByStatus(String status) {
        return userStoryRepo.findByStatus(Status.valueOf(status))
                .stream()
                .map(this::convertToUserStoryDTO)
                .collect(Collectors.toList());
    }

    // Méthode pour récupérer les user stories par priorité
    public List<UserStoryDTO> getUserStoriesByPriority(String priority) {
        return userStoryRepo.findByPriority(Priority.valueOf(priority))
                .stream()
                .map(this::convertToUserStoryDTO)
                .collect(Collectors.toList());
    }
}