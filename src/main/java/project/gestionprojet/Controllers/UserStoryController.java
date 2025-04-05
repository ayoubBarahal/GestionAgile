package project.gestionprojet.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gestionprojet.DTO.UserStoryDTO;
import project.gestionprojet.Service.UserStoryService;
import project.gestionprojet.ServiceImpl.UserStoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/userStory")
public class UserStoryController {

    @Autowired
    private UserStoryService userStoryService;

    @PostMapping("/createUserStory")
    public ResponseEntity<UserStoryDTO> createUserStory(@RequestBody UserStoryDTO userStoryDTO) {
        UserStoryDTO createdUserStory = userStoryService.createUserStory(userStoryDTO);
        return ResponseEntity.ok(createdUserStory);
    }

    @PutMapping("/updateUserStory")
    public ResponseEntity<UserStoryDTO> updateUserStory(@RequestBody UserStoryDTO userStoryDTO) {
        UserStoryDTO updatedUserStory = userStoryService.updateUserStory(userStoryDTO);
        if (updatedUserStory != null) {
            return ResponseEntity.ok(updatedUserStory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteUserStory/{id}")
    public ResponseEntity<UserStoryDTO> deleteUserStory(@PathVariable int id) {
        UserStoryDTO userStoryDTO = new UserStoryDTO();
        userStoryDTO.setIdUserStory(id);
        UserStoryDTO deletedUserStory = userStoryService.deleteUserStory(userStoryDTO);
        if (deletedUserStory != null) {
            return ResponseEntity.ok(deletedUserStory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getUserStoryById/{id}")
    public ResponseEntity<UserStoryDTO> getUserStoryById(@PathVariable int id) {
        UserStoryDTO userStoryDTO = userStoryService.getUserStoryById(id);
        if (userStoryDTO != null) {
            return ResponseEntity.ok(userStoryDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getUserStoryByUserName/{userName}")
    public ResponseEntity<UserStoryDTO> getUserStoryByUserName(@PathVariable String userName) {
        UserStoryDTO userStoryDTO = userStoryService.getUserStoryByUserName(userName);
        if (userStoryDTO != null) {
            return ResponseEntity.ok(userStoryDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllUserStories")
    public ResponseEntity<List<UserStoryDTO>> getAllUserStories() {
        List<UserStoryDTO> userStories = ((UserStoryServiceImpl) userStoryService).getAllUserStories();
        return ResponseEntity.ok(userStories);
    }

    @GetMapping("/getUserStoriesByEpicId/{epicId}")
    public ResponseEntity<List<UserStoryDTO>> getUserStoriesByEpicId(@PathVariable int epicId) {
        List<UserStoryDTO> userStories = ((UserStoryServiceImpl) userStoryService).getUserStoriesByEpicId(epicId);
        return ResponseEntity.ok(userStories);
    }

    @GetMapping("/getUserStoriesByStatus/{status}")
    public ResponseEntity<List<UserStoryDTO>> getUserStoriesByStatus(@PathVariable String status) {
        try {
            List<UserStoryDTO> userStories = ((UserStoryServiceImpl) userStoryService).getUserStoriesByStatus(status);
            return ResponseEntity.ok(userStories);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/getUserStoriesByPriority/{priority}")
    public ResponseEntity<List<UserStoryDTO>> getUserStoriesByPriority(@PathVariable String priority) {
        try {
            List<UserStoryDTO> userStories = ((UserStoryServiceImpl) userStoryService).getUserStoriesByPriority(priority);
            return ResponseEntity.ok(userStories);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}