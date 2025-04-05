package project.gestionprojet.Repositories;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gestionprojet.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(@NotBlank @Size(max = 50) @Email String email);
}
