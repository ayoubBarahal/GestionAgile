package project.gestionprojet.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.ERole;
import project.gestionprojet.Entities.Role;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
