package project.gestionprojet.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EpicDTO {
    private int idEpic;
    private String titre;
    private String description;
    private int idProductBacklog ;
    private int idSprintBacklog ;

    public EpicDTO(String titre,String description, int idProductBacklog, int idSprintBacklog) {
        this.titre = titre;
        this.description = description;
        this.idProductBacklog = idProductBacklog;
        this.idSprintBacklog = idSprintBacklog;
    }
}
