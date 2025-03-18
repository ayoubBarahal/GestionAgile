package project.gestionprojet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import project.gestionprojet.DTO.SprintBacklogDTO;
import project.gestionprojet.Entities.SprintBacklog;
import project.gestionprojet.Repositories.SprintBacklogRepo;
import project.gestionprojet.ServiceImpl.SprintBacklogServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SprintBacklogServiceImplTest {

    @Mock
    private SprintBacklogRepo sprintBacklogRepo;

    @InjectMocks
    private SprintBacklogServiceImpl sprintBacklogService;

    private SprintBacklog sprintBacklog;
    private SprintBacklogDTO sprintBacklogDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialisation des objets de test
        sprintBacklog = new SprintBacklog();
        sprintBacklog.setIdSprintBacklog(1);
        sprintBacklog.setNom("Sprint 1");
        sprintBacklog.setDescription("Description du Sprint 1");

        sprintBacklogDTO = new SprintBacklogDTO();
        sprintBacklogDTO.setIdSprintBacklog(1);
        sprintBacklogDTO.setNom("Sprint 1");
        sprintBacklogDTO.setDescription("Description du Sprint 1");
    }

    @Test
    public void testCreateSprintBacklog() {
        // Configuration du mock
        when(sprintBacklogRepo.save(any(SprintBacklog.class))).thenAnswer(invocation -> {
            SprintBacklog savedSprintBacklog = invocation.getArgument(0);
            savedSprintBacklog.setIdSprintBacklog(1);
            return savedSprintBacklog;
        });

        // Création du DTO d'entrée
        SprintBacklogDTO inputDTO = new SprintBacklogDTO();
        inputDTO.setNom("Sprint 1");
        inputDTO.setDescription("Description du Sprint 1");

        // Exécution de la méthode à tester
        SprintBacklogDTO result = sprintBacklogService.createSprintBacklog(inputDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getIdSprintBacklog());
        assertEquals("Sprint 1", result.getNom());
        assertEquals("Description du Sprint 1", result.getDescription());
        verify(sprintBacklogRepo, times(1)).save(any(SprintBacklog.class));
    }

    @Test
    public void testGetSprintBacklog() {
        // Configuration du mock
        when(sprintBacklogRepo.findById(1)).thenReturn(Optional.of(sprintBacklog));

        // Exécution de la méthode à tester
        SprintBacklogDTO result = sprintBacklogService.getSprintBacklog(1);

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getIdSprintBacklog());
        assertEquals("Sprint 1", result.getNom());
        assertEquals("Description du Sprint 1", result.getDescription());
        verify(sprintBacklogRepo, times(1)).findById(1);
    }

    @Test
    public void testGetSprintBacklogs() {
        // Configuration du mock
        SprintBacklog sprintBacklog2 = new SprintBacklog();
        sprintBacklog2.setIdSprintBacklog(2);
        sprintBacklog2.setNom("Sprint 2");
        sprintBacklog2.setDescription("Description du Sprint 2");

        List<SprintBacklog> sprintBacklogs = Arrays.asList(sprintBacklog, sprintBacklog2);
        when(sprintBacklogRepo.findAll()).thenReturn(sprintBacklogs);

        // Exécution de la méthode à tester
        List<SprintBacklogDTO> results = sprintBacklogService.getSprintBacklogs();

        // Vérifications
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Sprint 1", results.get(0).getNom());
        assertEquals("Sprint 2", results.get(1).getNom());
        verify(sprintBacklogRepo, times(1)).findAll();
    }

    @Test
    public void testUpdateSprintBacklog() {
        // Configuration du mock
        SprintBacklog updatedSprintBacklog = new SprintBacklog();
        updatedSprintBacklog.setIdSprintBacklog(1);
        updatedSprintBacklog.setNom("Sprint 1 Updated");
        updatedSprintBacklog.setDescription("Description mise à jour");

        when(sprintBacklogRepo.save(any(SprintBacklog.class))).thenReturn(updatedSprintBacklog);

        // Création du DTO d'entrée pour la mise à jour
        SprintBacklogDTO inputDTO = new SprintBacklogDTO();
        inputDTO.setIdSprintBacklog(1);
        inputDTO.setNom("Sprint 1 Updated");
        inputDTO.setDescription("Description mise à jour");

        // Exécution de la méthode à tester
        SprintBacklogDTO result = sprintBacklogService.updateSprintBacklog(inputDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Sprint 1 Updated", result.getNom());
        assertEquals("Description mise à jour", result.getDescription());
        verify(sprintBacklogRepo, times(1)).save(any(SprintBacklog.class));
    }

    @Test
    public void testDeleteSprintBacklog() {
        // Configuration du mock
        when(sprintBacklogRepo.findById(1)).thenReturn(Optional.of(sprintBacklog));
        doNothing().when(sprintBacklogRepo).delete(any(SprintBacklog.class));

        // Exécution de la méthode à tester
        sprintBacklogService.deleteSprintBacklog(1);

        // Vérifications
        verify(sprintBacklogRepo, times(1)).findById(1);
        verify(sprintBacklogRepo, times(1)).delete(any(SprintBacklog.class));
    }

    @Test
    public void testConvertToDto_SingleEntity() {
        // Exécution de la méthode à tester
        SprintBacklogDTO result = sprintBacklogService.convertToDto(sprintBacklog);

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getIdSprintBacklog());
        assertEquals("Sprint 1", result.getNom());
        assertEquals("Description du Sprint 1", result.getDescription());
    }

    @Test
    public void testConvertToEntity() {
        // Exécution de la méthode à tester
        SprintBacklog result = sprintBacklogService.convertToEntity(sprintBacklogDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Sprint 1", result.getNom());
        assertEquals("Description du Sprint 1", result.getDescription());
        // Notez que l'ID n'est pas défini dans la méthode convertToEntity
    }

    @Test
    public void testConvertToDto_ListOfEntities() {
        // Préparation des données de test
        SprintBacklog sprintBacklog2 = new SprintBacklog();
        sprintBacklog2.setIdSprintBacklog(2);
        sprintBacklog2.setNom("Sprint 2");
        sprintBacklog2.setDescription("Description du Sprint 2");

        List<SprintBacklog> sprintBacklogs = Arrays.asList(sprintBacklog, sprintBacklog2);

        // Exécution de la méthode à tester
        List<SprintBacklogDTO> results = sprintBacklogService.convertToDto(sprintBacklogs);

        // Vérifications
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(1, results.get(0).getIdSprintBacklog());
        assertEquals("Sprint 1", results.get(0).getNom());
        assertEquals(2, results.get(1).getIdSprintBacklog());
        assertEquals("Sprint 2", results.get(1).getNom());
    }
}