package project.gestionprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.gestionprojet.Configuration.JwtTokenProvider;
import project.gestionprojet.DTO.JwtResponse;
import project.gestionprojet.DTO.LoginRequest;
import project.gestionprojet.DTO.SignupRequest;
import project.gestionprojet.DTO.UserDetailsImpl;
import project.gestionprojet.Entities.ERole;
import project.gestionprojet.Entities.User;
import project.gestionprojet.Exceptions.UserExceptions;
import project.gestionprojet.Repositories.UserRepository;
import project.gestionprojet.ServiceImpl.AuthServiceImpl;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private LoginRequest loginRequest;
    private SignupRequest signupRequest;
    private Authentication authentication;
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        userDetails = new UserDetailsImpl(
                1,
                "testuser",
                "test@example.com",
                "encoded_password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        authentication = mock(Authentication.class);

        lenient().when(authentication.getPrincipal()).thenReturn(userDetails);

        signupRequest = new SignupRequest();
        signupRequest.setUsername("newuser");
        signupRequest.setEmail("newuser@example.com");
        signupRequest.setPassword("password");
        signupRequest.setRole("product_owner");
    }

    @Test
    void testAuthenticateUser() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenProvider.generateToken(authentication)).thenReturn("test_jwt_token");

        JwtResponse response = authService.authenticateUser(loginRequest);

        assertNotNull(response);
        assertEquals("test_jwt_token", response.getToken());
        assertEquals(1, response.getId());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider).generateToken(authentication);
    }

    @Test
    void testRegisterUser_Success() throws UserExceptions {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encoded_password");

        authService.registerUser(signupRequest);

        verify(userRepository).existsByUsername("newuser");
        verify(userRepository).existsByEmail("newuser@example.com");
        verify(passwordEncoder).encode("password");

        verify(userRepository).save(argThat(user ->
                user.getUsername().equals("newuser") &&
                        user.getEmail().equals("newuser@example.com") &&
                        user.getPassword().equals("encoded_password") &&
                        user.getRole() == ERole.ROLE_PRODUCT_OWNER
        ));
    }

    @Test
    void testRegisterUser_UsernameExists() {
        when(userRepository.existsByUsername("newuser")).thenReturn(true);

        Exception exception = assertThrows(UserExceptions.class, () -> {
            authService.registerUser(signupRequest);
        });

        assertEquals("Error: Username is already taken!", exception.getMessage());

        verify(userRepository).existsByUsername("newuser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUser_EmailExists() {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(true);

        Exception exception = assertThrows(UserExceptions.class, () -> {
            authService.registerUser(signupRequest);
        });

        assertEquals("Error: Email is already in use!", exception.getMessage());

        verify(userRepository).existsByUsername("newuser");
        verify(userRepository).existsByEmail("newuser@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUser_DefaultRole() throws UserExceptions {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encoded_password");

        signupRequest.setRole(null);

        authService.registerUser(signupRequest);

        verify(userRepository).save(argThat(user ->
                user.getRole() == ERole.ROLE_DEVELOPER
        ));
    }

    @Test
    void testRegisterUser_ScrumMasterRole() throws UserExceptions {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encoded_password");

        signupRequest.setRole("scrum_master");

        authService.registerUser(signupRequest);

        verify(userRepository).save(argThat(user ->
                user.getRole() == ERole.ROLE_SCRUM_MASTER
        ));
    }

    @Test
    void testExistsByUsername() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        boolean result = userRepository.existsByUsername("testuser");

        assertTrue(result);
        verify(userRepository).existsByUsername("testuser");
    }

    @Test
    void testExistsByEmail() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean result = userRepository.existsByEmail("test@example.com");

        assertTrue(result);
        verify(userRepository).existsByEmail("test@example.com");
    }
}