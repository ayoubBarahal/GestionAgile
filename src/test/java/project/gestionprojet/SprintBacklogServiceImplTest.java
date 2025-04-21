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
        when(sprintBacklogRepo.save(any(SprintBacklog.class))).thenAnswer(invocation -> {
            SprintBacklog savedSprintBacklog = invocation.getArgument(0);
            savedSprintBacklog.setIdSprintBacklog(1);
            return savedSprintBacklog;
        });

        SprintBacklogDTO inputDTO = new SprintBacklogDTO();
        inputDTO.setNom("Sprint 1");
        inputDTO.setDescription("Description du Sprint 1");

        SprintBacklogDTO result = sprintBacklogService.createSprintBacklog(inputDTO);

        assertNotNull(result);
        assertEquals(1, result.getIdSprintBacklog());
        assertEquals("Sprint 1", result.getNom());
        assertEquals("Description du Sprint 1", result.getDescription());
        verify(sprintBacklogRepo, times(1)).save(any(SprintBacklog.class));
    }

    @Test
    public void testGetSprintBacklog() {
        when(sprintBacklogRepo.findById(1)).thenReturn(Optional.of(sprintBacklog));

        SprintBacklogDTO result = sprintBacklogService.getSprintBacklog(1);

        assertNotNull(result);
        assertEquals(1, result.getIdSprintBacklog());
        assertEquals("Sprint 1", result.getNom());
        assertEquals("Description du Sprint 1", result.getDescription());
        verify(sprintBacklogRepo, times(1)).findById(1);
    }

    @Test
    public void testGetSprintBacklogs() {
        SprintBacklog sprintBacklog2 = new SprintBacklog();
        sprintBacklog2.setIdSprintBacklog(2);
        sprintBacklog2.setNom("Sprint 2");
        sprintBacklog2.setDescription("Description du Sprint 2");

        List<SprintBacklog> sprintBacklogs = Arrays.asList(sprintBacklog, sprintBacklog2);
        when(sprintBacklogRepo.findAll()).thenReturn(sprintBacklogs);

        List<SprintBacklogDTO> results = sprintBacklogService.getSprintBacklogs();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Sprint 1", results.get(0).getNom());
        assertEquals("Sprint 2", results.get(1).getNom());
        verify(sprintBacklogRepo, times(1)).findAll();
    }

    @Test
    public void testUpdateSprintBacklog() {
        when(sprintBacklogRepo.findById(1)).thenReturn(Optional.of(sprintBacklog));

        SprintBacklog updatedSprintBacklog = new SprintBacklog();
        updatedSprintBacklog.setIdSprintBacklog(1);
        updatedSprintBacklog.setNom("Sprint 1 Updated");
        updatedSprintBacklog.setDescription("Description mise à jour");

        when(sprintBacklogRepo.save(any(SprintBacklog.class))).thenReturn(updatedSprintBacklog);

        SprintBacklogDTO inputDTO = new SprintBacklogDTO();
        inputDTO.setNom("Sprint 1 Updated");
        inputDTO.setDescription("Description mise à jour");

        SprintBacklogDTO result = sprintBacklogService.updateSprintBacklog(1, inputDTO);

        assertNotNull(result);
        assertEquals("Sprint 1 Updated", result.getNom());
        assertEquals("Description mise à jour", result.getDescription());
        verify(sprintBacklogRepo, times(1)).findById(1);
        verify(sprintBacklogRepo, times(1)).save(any(SprintBacklog.class));
    }
    @Test
    public void testDeleteSprintBacklog() {
        when(sprintBacklogRepo.findById(1)).thenReturn(Optional.of(sprintBacklog));
        doNothing().when(sprintBacklogRepo).delete(any(SprintBacklog.class));

        sprintBacklogService.deleteSprintBacklog(1);

        verify(sprintBacklogRepo, times(1)).findById(1);
        verify(sprintBacklogRepo, times(1)).delete(any(SprintBacklog.class));
    }

    @Test
    public void testConvertToDto_SingleEntity() {
        SprintBacklogDTO result = sprintBacklogService.convertToDto(sprintBacklog);

        assertNotNull(result);
        assertEquals(1, result.getIdSprintBacklog());
        assertEquals("Sprint 1", result.getNom());
        assertEquals("Description du Sprint 1", result.getDescription());
    }

    @Test
    public void testConvertToEntity() {
        SprintBacklog result = sprintBacklogService.convertToEntity(sprintBacklogDTO);

        assertNotNull(result);
        assertEquals("Sprint 1", result.getNom());
        assertEquals("Description du Sprint 1", result.getDescription());
    }

    @Test
    public void testConvertToDto_ListOfEntities() {
        SprintBacklog sprintBacklog2 = new SprintBacklog();
        sprintBacklog2.setIdSprintBacklog(2);
        sprintBacklog2.setNom("Sprint 2");
        sprintBacklog2.setDescription("Description du Sprint 2");

        List<SprintBacklog> sprintBacklogs = Arrays.asList(sprintBacklog, sprintBacklog2);

        List<SprintBacklogDTO> results = sprintBacklogService.convertToDto(sprintBacklogs);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(1, results.get(0).getIdSprintBacklog());
        assertEquals("Sprint 1", results.get(0).getNom());
        assertEquals(2, results.get(1).getIdSprintBacklog());
        assertEquals("Sprint 2", results.get(1).getNom());
    }
}