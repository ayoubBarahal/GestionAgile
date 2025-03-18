package project.gestionprojet.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSprint ;
    private String nomSprint ;
    private Date dateDebut ;
    private Date dateFin ;

    @ManyToOne
    private SprintBacklog sprintBacklog ;

}
