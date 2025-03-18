package project.gestionprojet.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EpicDTO {
    private int idEpic;
    private String titre;
    private String description;
    private int idProductBacklog;
    private int idSprint;
}
