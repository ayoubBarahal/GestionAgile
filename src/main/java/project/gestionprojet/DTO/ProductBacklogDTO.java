package project.gestionprojet.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gestionprojet.Entities.Projet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBacklogDTO {
    private int idProductBacklog ;
    private String nom ;
    private Projet projet ;

}
