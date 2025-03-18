package project.gestionprojet.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Entities.Priority;
import project.gestionprojet.Entities.Status;

import java.security.Principal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStoryDTO {

    private int idUserStory;
    private String titre ;
    private String description ;
    private String desire;
    private String goal ;
    private String role ;
    private Priority priority;
    private Status status;
    private int idEpic;

}
