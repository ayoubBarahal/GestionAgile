package project.gestionprojet.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SprintBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSprintBacklog ;

    private String nom ;
    private String description ;

    @OneToMany(mappedBy = "sprintBacklog")
    private List<Sprint> sprints;

    @OneToMany(mappedBy = "sprintBacklog")
    private List<Epic> epics;

    public SprintBacklog(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

}
