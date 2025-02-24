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
public class ProductBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProductBacklog ;
    private String nom ;

    @OneToMany
    private List<Epic> epics ;

    @OneToMany
    private List<UserStory> userStories ;

    public ProductBacklog(String nom ){
        this.nom=nom ;
    }


}
