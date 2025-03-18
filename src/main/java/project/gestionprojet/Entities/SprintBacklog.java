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
    private String description ;

    @OneToMany
    private List<Epic> epics ;

    @OneToMany
    private List<Sprint> sprints ;

}
