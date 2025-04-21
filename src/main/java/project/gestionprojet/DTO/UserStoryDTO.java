package project.gestionprojet.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gestionprojet.Entities.Priority;
import project.gestionprojet.Entities.Status;

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
