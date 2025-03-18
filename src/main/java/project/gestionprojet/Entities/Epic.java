package project.gestionprojet.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Epic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEpic;
    private String titre;
    private String description;

    @ManyToOne
    private ProductBacklog productBacklog;

    @ManyToOne
    private SprintBacklog sprintBacklogs;

    @OneToMany(mappedBy = "epic")
    private List<UserStory> userStories;

}
