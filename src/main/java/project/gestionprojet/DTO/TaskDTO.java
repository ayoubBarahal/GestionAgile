package project.gestionprojet.DTO;

import lombok.*;
import project.gestionprojet.Entities.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    private int idTask;
    private String title;
    private String description;
    private Status status;
    private int idUserStory ;
}
