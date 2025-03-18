package project.gestionprojet.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProjet;
    private String nomProjet;

    @OneToMany
    private List<ProductBacklog> productBacklogs;

    public Projet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

}
