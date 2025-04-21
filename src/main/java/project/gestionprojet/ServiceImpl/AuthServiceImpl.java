package project.gestionprojet.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.gestionprojet.Configuration.JwtTokenProvider;
import project.gestionprojet.DTO.*;
import project.gestionprojet.Entities.ERole;
import project.gestionprojet.Entities.User;
import project.gestionprojet.Exceptions.UserExceptions;
import project.gestionprojet.Repositories.UserRepository;
import project.gestionprojet.Service.AuthService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public MessageResponse registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserExceptions("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserExceptions("Error: Email is already in use!");
        }

        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );

        String strRole = signUpRequest.getRole();

        if (strRole == null || strRole.isEmpty()) {
            user.setRole(ERole.ROLE_DEVELOPER);
        } else {
            switch (strRole) {
                case "product_owner":
                    user.setRole(ERole.ROLE_PRODUCT_OWNER);
                    break;
                case "scrum_master":
                    user.setRole(ERole.ROLE_SCRUM_MASTER);
                    break;
                default:
                    user.setRole(ERole.ROLE_DEVELOPER);
            }
        }

        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

}