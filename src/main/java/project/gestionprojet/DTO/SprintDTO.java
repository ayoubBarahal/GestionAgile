package project.gestionprojet.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SprintDTO {
    private int idSprint;
    private String nomSprint;
    private Date dateDebut ;
    private Date dateFin ;
    private int idSprintBacklog ;
}
