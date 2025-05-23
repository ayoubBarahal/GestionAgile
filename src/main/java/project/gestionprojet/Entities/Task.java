package project.gestionprojet.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTask ;
    private String title ;
    private String description ;

    @Enumerated(EnumType.STRING)
    private Status status ;

    @ManyToOne
    private UserStory userStory ;

}
