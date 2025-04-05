package project.gestionprojet.Controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.gestionprojet.Configuration.JwtTokenProvider;
import project.gestionprojet.DTO.*;
import project.gestionprojet.Entities.ERole;
import project.gestionprojet.Entities.Role;
import project.gestionprojet.Entities.User;
import project.gestionprojet.Repositories.RoleRepository;
import project.gestionprojet.Repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );

        String strRole = signUpRequest.getRole();

        if (strRole == null || strRole.isEmpty()) {
            Role developerRole = roleRepository.findByName(ERole.ROLE_DEVELOPER)
                    .orElseThrow(() -> new RuntimeException("Error: Developer role not found"));
            user.setRole(developerRole);
        } else {
            switch (strRole) {
                case "product_owner":
                    Role productOwnerRole = roleRepository.findByName(ERole.ROLE_PRODUCT_OWNER)
                            .orElseThrow(() -> new RuntimeException("Error: Product Owner role not found"));
                    user.setRole(productOwnerRole);
                    break;
                case "scrum_master":
                    Role scrumMasterRole = roleRepository.findByName(ERole.ROLE_SCRUM_MASTER)
                            .orElseThrow(() -> new RuntimeException("Error: Scrum Master role not found"));
                    user.setRole(scrumMasterRole);
                    break;
                default:
                    Role developerRole = roleRepository.findByName(ERole.ROLE_DEVELOPER)
                            .orElseThrow(() -> new RuntimeException("Error: Developer role not found"));
                    user.setRole(developerRole);
            }
        }

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}