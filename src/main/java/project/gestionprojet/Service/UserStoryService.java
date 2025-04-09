package project.gestionprojet.Service;

import project.gestionprojet.DTO.UserStoryDTO;
import project.gestionprojet.Entities.UserStory;

public interface UserStoryService {
    UserStoryDTO createUserStory(UserStoryDTO userStoryDTO);
    UserStoryDTO updateUserStory(int id, UserStoryDTO userStoryDTO);
    UserStoryDTO deleteUserStory(UserStoryDTO userStoryDTO);
    UserStoryDTO getUserStoryById(int id);
    UserStoryDTO getUserStoryByUserName(String userName);


}
