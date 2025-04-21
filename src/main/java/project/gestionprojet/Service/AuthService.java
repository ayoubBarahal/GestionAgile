package project.gestionprojet.Service;

import project.gestionprojet.DTO.JwtResponse;
import project.gestionprojet.DTO.LoginRequest;
import project.gestionprojet.DTO.MessageResponse;
import project.gestionprojet.DTO.SignupRequest;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    MessageResponse registerUser(SignupRequest signUpRequest);

}
