package project.gestionprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import project.gestionprojet.DTO.UserStoryDTO;
import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Entities.Priority;
import project.gestionprojet.Entities.Status;
import project.gestionprojet.Entities.UserStory;
import project.gestionprojet.Repositories.EpicRepo;
import project.gestionprojet.Repositories.UserStoryRepo;
import project.gestionprojet.ServiceImpl.UserStoryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserStoryServiceImplTest {

    @Mock
    private UserStoryRepo userStoryRepo;

    @Mock
    private EpicRepo epicRepo;

    @InjectMocks
    private UserStoryServiceImpl userStoryService;

    @Captor
    private ArgumentCaptor<UserStory> userStoryCaptor;

    private UserStory userStory;
    private UserStoryDTO userStoryDTO;
    private Epic epic;
    private List<UserStory> userStoryList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        epic = new Epic();
        epic.setIdEpic(1);
        epic.setTitre("Epic Test");
        epic.setDescription("Epic Description Test");

        userStory = new UserStory();
        userStory.setIdUserStory(1);
        userStory.setTitre("User Story Test");
        userStory.setDescription("Description Test");
        userStory.setDesire("Desire Test");
        userStory.setGoal("Goal Test");
        userStory.setRole("Role Test");
        userStory.setPriority(Priority.MustDo);
        userStory.setStatus(Status.ToDo);
        userStory.setEpic(epic);

        userStoryDTO = new UserStoryDTO();
        userStoryDTO.setIdUserStory(1);
        userStoryDTO.setTitre("User Story Test");
        userStoryDTO.setDescription("Description Test");
        userStoryDTO.setDesire("Desire Test");
        userStoryDTO.setGoal("Goal Test");
        userStoryDTO.setRole("Role Test");
        userStoryDTO.setPriority(Priority.MustDo);
        userStoryDTO.setStatus(Status.ToDo);
        userStoryDTO.setIdEpic(1);

        userStoryList = new ArrayList<>();
        userStoryList.add(userStory);

        UserStory userStory2 = new UserStory();
        userStory2.setIdUserStory(2);
        userStory2.setTitre("User Story Test 2");
        userStory2.setDescription("Description Test 2");
        userStory2.setDesire("Desire Test 2");
        userStory2.setGoal("Goal Test 2");
        userStory2.setRole("Role Test 2");
        userStory2.setPriority(Priority.ShouldDo);
        userStory2.setStatus(Status.InProgress);
        userStory2.setEpic(epic);
        userStoryList.add(userStory2);
    }

    @Nested
    @DisplayName("Tests pour les méthodes de conversion")
    class ConversionMethodsTests {

        @Test
        @DisplayName("Devrait convertir User Story en DTO correctement")
        void shouldConvertUserStoryToDTO() {
            UserStoryDTO result = userStoryService.convertToUserStoryDTO(userStory);

            assertNotNull(result);
            assertEquals(userStory.getIdUserStory(), result.getIdUserStory());
            assertEquals(userStory.getTitre(), result.getTitre());
            assertEquals(userStory.getDescription(), result.getDescription());
            assertEquals(userStory.getDesire(), result.getDesire());
            assertEquals(userStory.getGoal(), result.getGoal());
            assertEquals(userStory.getRole(), result.getRole());
            assertEquals(userStory.getPriority(), result.getPriority());
            assertEquals(userStory.getStatus(), result.getStatus());
            assertEquals(userStory.getEpic().getIdEpic(), result.getIdEpic());
        }

        @Test
        @DisplayName("Devrait retourner null lors de la conversion d'une User Story null")
        void shouldReturnNullWhenConvertingNullUserStory() {
            UserStoryDTO result = userStoryService.convertToUserStoryDTO(null);
            assertNull(result);
        }

        @Test
        @DisplayName("Devrait convertir DTO en User Story correctement")
        void shouldConvertDTOToUserStory() {
            when(epicRepo.findById(anyInt())).thenReturn(Optional.of(epic));

            UserStory result = userStoryService.convertToUserStory(userStoryDTO);

            assertNotNull(result);
            assertEquals(userStoryDTO.getIdUserStory(), result.getIdUserStory());
            assertEquals(userStoryDTO.getTitre(), result.getTitre());
            assertEquals(userStoryDTO.getDescription(), result.getDescription());
            assertEquals(userStoryDTO.getDesire(), result.getDesire());
            assertEquals(userStoryDTO.getGoal(), result.getGoal());
            assertEquals(userStoryDTO.getRole(), result.getRole());
            assertEquals(userStoryDTO.getPriority(), result.getPriority());
            assertEquals(userStoryDTO.getStatus(), result.getStatus());
            assertEquals(epic, result.getEpic());

            verify(epicRepo).findById(userStoryDTO.getIdEpic());
        }

        @Test
        @DisplayName("Devrait retourner null lors de la conversion d'un DTO null")
        void shouldReturnNullWhenConvertingNullDTO() {
            UserStory result = userStoryService.convertToUserStory(null);
            assertNull(result);
        }
    }

    @Nested
    @DisplayName("Tests pour createUserStory")
    class CreateUserStoryTests {

        @Test
        @DisplayName("Devrait créer une user story avec succès")
        void shouldCreateUserStorySuccessfully() {
            when(epicRepo.findById(anyInt())).thenReturn(Optional.of(epic));
            when(userStoryRepo.save(any(UserStory.class))).thenReturn(userStory);

            UserStoryDTO result = userStoryService.createUserStory(userStoryDTO);

            assertNotNull(result);
            assertEquals(userStoryDTO.getTitre(), result.getTitre());
            assertEquals(userStoryDTO.getDescription(), result.getDescription());
            assertEquals(userStoryDTO.getIdEpic(), result.getIdEpic());

            verify(epicRepo).findById(userStoryDTO.getIdEpic());
            verify(userStoryRepo).save(userStoryCaptor.capture());

            UserStory capturedUserStory = userStoryCaptor.getValue();
            assertEquals(userStoryDTO.getTitre(), capturedUserStory.getTitre());
            assertEquals(userStoryDTO.getDescription(), capturedUserStory.getDescription());
        }

        @Test
        @DisplayName("Devrait gérer le cas où Epic est null")
        void shouldHandleNullEpic() {
            when(epicRepo.findById(anyInt())).thenReturn(Optional.empty());

            UserStory userStoryWithoutEpic = new UserStory();
            userStoryWithoutEpic.setIdUserStory(1);
            userStoryWithoutEpic.setTitre("User Story Test");
            userStoryWithoutEpic.setEpic(null);

            when(userStoryRepo.save(any(UserStory.class))).thenReturn(userStoryWithoutEpic);

            assertThrows(NullPointerException.class, () -> {
                userStoryService.createUserStory(userStoryDTO);
            });

            verify(epicRepo).findById(userStoryDTO.getIdEpic());
            verify(userStoryRepo).save(any(UserStory.class));
        }
    }

    @Nested
    @DisplayName("Tests pour updateUserStory")
    class UpdateUserStoryTests {

        @Test
        @DisplayName("Devrait mettre à jour une user story avec succès")
        void shouldUpdateUserStorySuccessfully() {
            when(userStoryRepo.findById(anyInt())).thenReturn(Optional.of(userStory));
            when(epicRepo.findById(anyInt())).thenReturn(Optional.of(epic));

            UserStory updatedUserStory = new UserStory();
            updatedUserStory.setIdUserStory(1);
            updatedUserStory.setTitre("Updated User Story");
            updatedUserStory.setDescription("Updated Description");
            updatedUserStory.setPriority(Priority.CouldDo);
            updatedUserStory.setStatus(Status.Done);
            updatedUserStory.setEpic(epic);

            when(userStoryRepo.save(any(UserStory.class))).thenReturn(updatedUserStory);

            UserStoryDTO updatedDTO = new UserStoryDTO();
            updatedDTO.setIdUserStory(1);
            updatedDTO.setTitre("Updated User Story");
            updatedDTO.setDescription("Updated Description");
            updatedDTO.setIdEpic(1);
            updatedDTO.setPriority(Priority.CouldDo);
            updatedDTO.setStatus(Status.Done);

            UserStoryDTO result = userStoryService.updateUserStory(1, updatedDTO);

            assertNotNull(result);
            assertEquals(updatedDTO.getTitre(), result.getTitre());
            assertEquals(updatedDTO.getDescription(), result.getDescription());
            assertEquals(updatedDTO.getPriority(), result.getPriority());
            assertEquals(updatedDTO.getStatus(), result.getStatus());

            verify(userStoryRepo).findById(1);
            verify(epicRepo, atLeastOnce()).findById(updatedDTO.getIdEpic());
            verify(userStoryRepo).save(any(UserStory.class));
        }

        @Test
        @DisplayName("Devrait retourner null quand la user story n'existe pas")
        void shouldReturnNullWhenUserStoryNotExists() {
            when(userStoryRepo.findById(anyInt())).thenReturn(Optional.empty());

            UserStoryDTO result = userStoryService.updateUserStory(999, userStoryDTO);

            assertNull(result);
            verify(userStoryRepo).findById(999);
            verify(userStoryRepo, never()).save(any(UserStory.class));
        }

        @Test
        @DisplayName("Devrait gérer correctement un changement d'Epic")
        void shouldHandleEpicChange() {
            when(userStoryRepo.findById(anyInt())).thenReturn(Optional.of(userStory));

            Epic newEpic = new Epic();
            newEpic.setIdEpic(2);
            newEpic.setTitre("New Epic");

            when(epicRepo.findById(2)).thenReturn(Optional.of(newEpic));

            UserStory updatedUserStory = new UserStory();
            updatedUserStory.setIdUserStory(1);
            updatedUserStory.setTitre("User Story Test");
            updatedUserStory.setEpic(newEpic);

            when(userStoryRepo.save(any(UserStory.class))).thenReturn(updatedUserStory);

            UserStoryDTO updatedDTO = new UserStoryDTO();
            updatedDTO.setIdUserStory(1);
            updatedDTO.setTitre("User Story Test");
            updatedDTO.setIdEpic(2);

            UserStoryDTO result = userStoryService.updateUserStory(1, updatedDTO);

            assertNotNull(result);
            assertEquals(2, result.getIdEpic());

            verify(userStoryRepo).findById(1);
            verify(epicRepo, atLeastOnce()).findById(2);
            verify(userStoryRepo).save(any(UserStory.class));
        }
    }

    @Nested
    @DisplayName("Tests pour deleteUserStory")
    class DeleteUserStoryTests {

        @Test
        @DisplayName("Devrait supprimer une user story avec succès")
        void shouldDeleteUserStorySuccessfully() {
            when(userStoryRepo.findById(anyInt())).thenReturn(Optional.of(userStory));
            doNothing().when(userStoryRepo).delete(any(UserStory.class));

            UserStoryDTO dto = new UserStoryDTO();
            dto.setIdUserStory(1);

            UserStoryDTO result = userStoryService.deleteUserStory(dto);

            assertNotNull(result);
            assertEquals(userStory.getIdUserStory(), result.getIdUserStory());
            assertEquals(userStory.getTitre(), result.getTitre());

            verify(userStoryRepo).findById(1);
            verify(userStoryRepo).delete(userStory);
        }

        @Test
        @DisplayName("Devrait retourner null quand la user story à supprimer n'existe pas")
        void shouldReturnNullWhenUserStoryToDeleteNotExists() {
            when(userStoryRepo.findById(anyInt())).thenReturn(Optional.empty());

            UserStoryDTO dto = new UserStoryDTO();
            dto.setIdUserStory(999);

            UserStoryDTO result = userStoryService.deleteUserStory(dto);

            assertNull(result);
            verify(userStoryRepo).findById(999);
            verify(userStoryRepo, never()).delete(any(UserStory.class));
        }
    }

    @Nested
    @DisplayName("Tests pour getUserStoryById")
    class GetUserStoryByIdTests {

        @Test
        @DisplayName("Devrait récupérer une user story par ID avec succès")
        void shouldGetUserStoryByIdSuccessfully() {
            when(userStoryRepo.findById(anyInt())).thenReturn(Optional.of(userStory));

            UserStoryDTO result = userStoryService.getUserStoryById(1);

            assertNotNull(result);
            assertEquals(userStory.getIdUserStory(), result.getIdUserStory());
            assertEquals(userStory.getTitre(), result.getTitre());

            verify(userStoryRepo).findById(1);
        }

        @Test
        @DisplayName("Devrait retourner null quand la user story n'existe pas")
        void shouldReturnNullWhenUserStoryNotExists() {
            when(userStoryRepo.findById(anyInt())).thenReturn(Optional.empty());

            UserStoryDTO result = userStoryService.getUserStoryById(999);

            assertNull(result);
            verify(userStoryRepo).findById(999);
        }
    }

    @Nested
    @DisplayName("Tests pour getUserStoryByUserName")
    class GetUserStoryByUserNameTests {

        @Test
        @DisplayName("Devrait récupérer une user story par titre avec succès")
        void shouldGetUserStoryByTitleSuccessfully() {
            when(userStoryRepo.findByTitre(anyString())).thenReturn(userStory);

            UserStoryDTO result = userStoryService.getUserStoryByUserName("User Story Test");

            assertNotNull(result);
            assertEquals(userStory.getIdUserStory(), result.getIdUserStory());
            assertEquals(userStory.getTitre(), result.getTitre());

            verify(userStoryRepo).findByTitre("User Story Test");
        }

        @Test
        @DisplayName("Devrait retourner null quand la user story n'existe pas par titre")
        void shouldReturnNullWhenUserStoryNotExistsByTitle() {
            when(userStoryRepo.findByTitre(anyString())).thenReturn(null);

            UserStoryDTO result = userStoryService.getUserStoryByUserName("Non-existent User Story");

            assertNull(result);
            verify(userStoryRepo).findByTitre("Non-existent User Story");
        }
    }

    @Nested
    @DisplayName("Tests pour getAllUserStories")
    class GetAllUserStoriesTests {

        @Test
        @DisplayName("Devrait récupérer toutes les user stories avec succès")
        void shouldGetAllUserStoriesSuccessfully() {
            when(userStoryRepo.findAll()).thenReturn(userStoryList);

            List<UserStoryDTO> results = userStoryService.getAllUserStories();

            assertNotNull(results);
            assertEquals(2, results.size());
            assertEquals(userStoryList.get(0).getIdUserStory(), results.get(0).getIdUserStory());
            assertEquals(userStoryList.get(0).getTitre(), results.get(0).getTitre());
            assertEquals(userStoryList.get(1).getIdUserStory(), results.get(1).getIdUserStory());
            assertEquals(userStoryList.get(1).getTitre(), results.get(1).getTitre());

            verify(userStoryRepo).findAll();
        }

        @Test
        @DisplayName("Devrait retourner une liste vide quand aucune user story n'existe")
        void shouldReturnEmptyListWhenNoUserStories() {
            when(userStoryRepo.findAll()).thenReturn(new ArrayList<>());

            List<UserStoryDTO> results = userStoryService.getAllUserStories();

            assertNotNull(results);
            assertTrue(results.isEmpty());

            verify(userStoryRepo).findAll();
        }
    }

    @Nested
    @DisplayName("Tests pour getUserStoriesByEpicId")
    class GetUserStoriesByEpicIdTests {

        @Test
        @DisplayName("Devrait récupérer les user stories par Epic ID avec succès")
        void shouldGetUserStoriesByEpicIdSuccessfully() {
            when(userStoryRepo.findByEpicIdEpic(anyInt())).thenReturn(userStoryList);

            List<UserStoryDTO> results = userStoryService.getUserStoriesByEpicId(1);

            assertNotNull(results);
            assertEquals(2, results.size());
            assertEquals(userStoryList.get(0).getIdUserStory(), results.get(0).getIdUserStory());
            assertEquals(userStoryList.get(0).getTitre(), results.get(0).getTitre());
            assertEquals(userStoryList.get(1).getIdUserStory(), results.get(1).getIdUserStory());
            assertEquals(userStoryList.get(1).getTitre(), results.get(1).getTitre());

            verify(userStoryRepo).findByEpicIdEpic(1);
        }

        @Test
        @DisplayName("Devrait retourner une liste vide quand aucune user story n'existe pour l'Epic")
        void shouldReturnEmptyListWhenNoUserStoriesForEpic() {
            when(userStoryRepo.findByEpicIdEpic(anyInt())).thenReturn(new ArrayList<>());

            List<UserStoryDTO> results = userStoryService.getUserStoriesByEpicId(999);

            assertNotNull(results);
            assertTrue(results.isEmpty());

            verify(userStoryRepo).findByEpicIdEpic(999);
        }
    }

    @Nested
    @DisplayName("Tests pour getUserStoriesByStatus")
    class GetUserStoriesByStatusTests {

        @Test
        @DisplayName("Devrait récupérer les user stories par Status avec succès")
        void shouldGetUserStoriesByStatusSuccessfully() {
            List<UserStory> todoUserStories = new ArrayList<>();
            todoUserStories.add(userStory);

            when(userStoryRepo.findByStatus(Status.ToDo)).thenReturn(todoUserStories);

            List<UserStoryDTO> results = userStoryService.getUserStoriesByStatus("ToDo");

            assertNotNull(results);
            assertEquals(1, results.size());
            assertEquals(todoUserStories.get(0).getIdUserStory(), results.get(0).getIdUserStory());
            assertEquals(todoUserStories.get(0).getTitre(), results.get(0).getTitre());
            assertEquals(Status.ToDo, results.get(0).getStatus());

            verify(userStoryRepo).findByStatus(Status.ToDo);
        }

        @Test
        @DisplayName("Devrait lancer une exception pour un status invalide")
        void shouldThrowExceptionForInvalidStatus() {
            assertThrows(IllegalArgumentException.class, () -> {
                userStoryService.getUserStoriesByStatus("InvalidStatus");
            });
        }
    }

    @Nested
    @DisplayName("Tests pour getUserStoriesByPriority")
    class GetUserStoriesByPriorityTests {

        @Test
        @DisplayName("Devrait récupérer les user stories par Priority avec succès")
        void shouldGetUserStoriesByPrioritySuccessfully() {
            List<UserStory> mustDoUserStories = new ArrayList<>();
            mustDoUserStories.add(userStory);

            when(userStoryRepo.findByPriority(Priority.MustDo)).thenReturn(mustDoUserStories);

            List<UserStoryDTO> results = userStoryService.getUserStoriesByPriority("MustDo");

            assertNotNull(results);
            assertEquals(1, results.size());
            assertEquals(mustDoUserStories.get(0).getIdUserStory(), results.get(0).getIdUserStory());
            assertEquals(mustDoUserStories.get(0).getTitre(), results.get(0).getTitre());
            assertEquals(Priority.MustDo, results.get(0).getPriority());

            verify(userStoryRepo).findByPriority(Priority.MustDo);
        }

        @Test
        @DisplayName("Devrait lancer une exception pour une priorité invalide")
        void shouldThrowExceptionForInvalidPriority() {
            assertThrows(IllegalArgumentException.class, () -> {
                userStoryService.getUserStoriesByPriority("InvalidPriority");
            });
        }
    }

    @Test
    @DisplayName("Test d'intégration des fonctionnalités")
    void testIntegrationOfFunctionalities() {
        reset(userStoryRepo, epicRepo);

        when(epicRepo.findById(1)).thenReturn(Optional.of(epic));

        UserStory createdEntity = new UserStory();
        createdEntity.setIdUserStory(1);
        createdEntity.setTitre("User Story Test");
        createdEntity.setEpic(epic);

        UserStory updatedEntity = new UserStory();
        updatedEntity.setIdUserStory(1);
        updatedEntity.setTitre("Updated User Story");
        updatedEntity.setDescription("Updated Description");
        updatedEntity.setStatus(Status.InProgress);
        updatedEntity.setEpic(epic);

        when(userStoryRepo.save(any(UserStory.class)))
                .thenReturn(createdEntity)
                .thenReturn(updatedEntity);

        when(userStoryRepo.findById(1)).thenReturn(Optional.of(createdEntity));
        doNothing().when(userStoryRepo).delete(any(UserStory.class));

        UserStoryDTO createdUserStory = userStoryService.createUserStory(userStoryDTO);

        UserStoryDTO retrievedUserStory = userStoryService.getUserStoryById(1);

        UserStoryDTO updatedDTO = new UserStoryDTO();
        updatedDTO.setIdUserStory(1);
        updatedDTO.setTitre("Updated User Story");
        updatedDTO.setDescription("Updated Description");
        updatedDTO.setStatus(Status.InProgress);
        updatedDTO.setIdEpic(1);

        UserStoryDTO updatedUserStory = userStoryService.updateUserStory(1, updatedDTO);

        UserStoryDTO deletedUserStory = userStoryService.deleteUserStory(updatedDTO);

        assertNotNull(createdUserStory);
        assertNotNull(retrievedUserStory);
        assertNotNull(updatedUserStory);
        assertNotNull(deletedUserStory);

        verify(epicRepo, atLeastOnce()).findById(1);
        verify(userStoryRepo, times(2)).save(any(UserStory.class));
        verify(userStoryRepo, atLeastOnce()).findById(1);
        verify(userStoryRepo).delete(any(UserStory.class));
    }
}