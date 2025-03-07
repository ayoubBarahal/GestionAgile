package project.gestionprojet.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUserStory;
    private String titre;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private String status;

    @ManyToOne
    private Epic epic;
    @OneToMany
    private List<Task> tasks ;

}
