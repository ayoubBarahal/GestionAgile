package project.gestionprojet.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetDTO {
    private int idProjet;
    private String nomProjet;

    public ProjetDTO(int idProjet, String nomProjet) {
        this.idProjet = idProjet;
        this.nomProjet = nomProjet;
    }
}
