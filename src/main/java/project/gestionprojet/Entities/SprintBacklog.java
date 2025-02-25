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
public class SprintBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSprintBacklog ;
    private String nom ;


    @OneToMany
    private List<Epic> epics ;

    public SprintBacklog(String nom) {
        this.nom = nom;
    }

}
