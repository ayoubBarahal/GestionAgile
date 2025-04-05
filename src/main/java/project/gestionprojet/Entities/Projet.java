package project.gestionprojet.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProjet;
    private String nomProjet;

    public Projet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

}
