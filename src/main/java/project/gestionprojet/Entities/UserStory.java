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
    private String desire ;
    private String goal;
    private String role ;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Epic epic;

    @OneToMany
    private List<Task> tasks ;




}
