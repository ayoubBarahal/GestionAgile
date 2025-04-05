package project.gestionprojet.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class SignupRequest {
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    private String role;

    @NotBlank
    private String password;
}