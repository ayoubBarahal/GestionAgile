package project.gestionprojet;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import project.gestionprojet.DTO.SprintDTO;
import project.gestionprojet.Entities.Sprint;
import project.gestionprojet.Entities.SprintBacklog;
import project.gestionprojet.Repositories.SprintRepo;
import project.gestionprojet.ServiceImpl.SprintServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SprintServiceImplTest {

    @Mock
    private SprintRepo sprintRepo;

    @InjectMocks
    private SprintServiceImpl sprintService;

    private Sprint sprint;
    private SprintDTO sprintDTO;
    private SprintBacklog sprintBacklog;
    private Date dateDebut;
    private Date dateFin;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        dateDebut = new Date();
        dateFin = new Date(dateDebut.getTime() + 1000 * 60 * 60 * 24 * 14);

        sprintBacklog = new SprintBacklog();
        sprintBacklog.setIdSprintBacklog(1);
        sprintBacklog.setNom("Backlog Test");
        sprintBacklog.setDescription("Description du backlog de test");

        sprint = new Sprint();
        sprint.setIdSprint(1);
        sprint.setNomSprint("Sprint 1");
        sprint.setDateDebut(dateDebut);
        sprint.setDateFin(dateFin);
        sprint.setSprintBacklog(sprintBacklog);

        sprintDTO = new SprintDTO();
        sprintDTO.setIdSprint(1);
        sprintDTO.setNomSprint("Sprint 1");
        sprintDTO.setDateDebut(dateDebut);
        sprintDTO.setDateFin(dateFin);
        sprintDTO.setIdSprintBacklog(1);
    }

    @Test
    public void testAddSprint() {
        when(sprintRepo.save(any(Sprint.class))).thenReturn(sprint);

        SprintDTO inputDTO = new SprintDTO();
        inputDTO.setNomSprint("Sprint 1");
        inputDTO.setDateDebut(dateDebut);
        inputDTO.setDateFin(dateFin);
        inputDTO.setIdSprintBacklog(1);

        SprintDTO result = sprintService.addSprint(inputDTO);

        assertNotNull(result);
        assertEquals("Sprint 1", result.getNomSprint());
        assertEquals(dateDebut, result.getDateDebut());
        assertEquals(dateFin, result.getDateFin());
        assertEquals(1, result.getIdSprintBacklog());
        verify(sprintRepo, times(1)).save(any(Sprint.class));
    }

    @Test
    public void testUpdateSprint_WhenSprintExists() {
        when(sprintRepo.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintRepo.save(any(Sprint.class))).thenReturn(sprint);

        SprintDTO updateDTO = new SprintDTO();
        updateDTO.setIdSprint(1);
        updateDTO.setNomSprint("Sprint 1 Updated");
        updateDTO.setDateDebut(dateDebut);
        updateDTO.setDateFin(dateFin);
        updateDTO.setIdSprintBacklog(1);

        SprintDTO result = sprintService.updateSprint(updateDTO);

        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        verify(sprintRepo, times(1)).findById(1);
        verify(sprintRepo, times(1)).save(any(Sprint.class));
    }

    @Test
    public void testUpdateSprint_WhenSprintDoesNotExist() {
        when(sprintRepo.findById(999)).thenReturn(Optional.empty());

        SprintDTO updateDTO = new SprintDTO();
        updateDTO.setIdSprint(999);
        updateDTO.setNomSprint("Non-Existing Sprint");

        SprintDTO result = sprintService.updateSprint(updateDTO);

        assertNull(result);
        verify(sprintRepo, times(1)).findById(999);
        verify(sprintRepo, never()).save(any(Sprint.class));
    }

    @Test
    public void testDeleteSprint_WhenSprintExists() {
        when(sprintRepo.findById(1)).thenReturn(Optional.of(sprint));
        doNothing().when(sprintRepo).delete(sprint);

        SprintDTO deleteDTO = new SprintDTO();
        deleteDTO.setIdSprint(1);

        SprintDTO result = sprintService.deleteSprint(deleteDTO);

        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        assertEquals("Sprint 1", result.getNomSprint());
        verify(sprintRepo, times(1)).findById(1);
        verify(sprintRepo, times(1)).delete(sprint);
    }

    @Test
    public void testDeleteSprint_WhenSprintDoesNotExist() {
        when(sprintRepo.findById(999)).thenReturn(Optional.empty());

        SprintDTO deleteDTO = new SprintDTO();
        deleteDTO.setIdSprint(999);

        SprintDTO result = sprintService.deleteSprint(deleteDTO);

        assertNull(result);
        verify(sprintRepo, times(1)).findById(999);
        verify(sprintRepo, never()).delete(any(Sprint.class));
    }

    @Test
    public void testGetAllSprint() {
        Sprint sprint2 = new Sprint();
        sprint2.setIdSprint(2);
        sprint2.setNomSprint("Sprint 2");
        sprint2.setDateDebut(dateDebut);
        sprint2.setDateFin(dateFin);
        sprint2.setSprintBacklog(sprintBacklog);

        when(sprintRepo.findAll()).thenReturn(Arrays.asList(sprint, sprint2));

        List<SprintDTO> results = sprintService.getAllSprint();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Sprint 1", results.get(0).getNomSprint());
        assertEquals("Sprint 2", results.get(1).getNomSprint());
        verify(sprintRepo, times(1)).findAll();
    }

    @Test
    public void testGetSprintById_WhenSprintExists() {
        when(sprintRepo.findById(1)).thenReturn(Optional.of(sprint));

        SprintDTO result = sprintService.getSprintById(1);

        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        assertEquals("Sprint 1", result.getNomSprint());
        verify(sprintRepo, times(1)).findById(1);
    }

    @Test
    public void testGetSprintById_WhenSprintDoesNotExist() {
        when(sprintRepo.findById(999)).thenReturn(Optional.empty());

        SprintDTO result = sprintService.getSprintById(999);

        assertNull(result);
        verify(sprintRepo, times(1)).findById(999);
    }

    @Test
    public void testGetSprintByName_WhenSprintExists() {
        when(sprintRepo.findByNomSprint("Sprint 1")).thenReturn(sprint);

        SprintDTO result = sprintService.getSprintByName("Sprint 1");

        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        assertEquals("Sprint 1", result.getNomSprint());
        verify(sprintRepo, times(1)).findByNomSprint("Sprint 1");
    }

    @Test
    public void testGetSprintByName_WhenSprintDoesNotExist() {
        when(sprintRepo.findByNomSprint("Non-Existing Sprint")).thenReturn(null);

        SprintDTO result = sprintService.getSprintByName("Non-Existing Sprint");
        assertNull(result);
        verify(sprintRepo, times(1)).findByNomSprint("Non-Existing Sprint");
    }

    @Test
    public void testConvertToSprintDTO() {
        SprintDTO result = sprintService.convertToSprintDTO(sprint);

        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        assertEquals("Sprint 1", result.getNomSprint());
        assertEquals(dateDebut, result.getDateDebut());
        assertEquals(dateFin, result.getDateFin());
        assertEquals(1, result.getIdSprintBacklog());
    }

    @Test
    public void testConvertToSprint() {
        Sprint result = sprintService.convertToSprint(sprintDTO);

        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        assertEquals("Sprint 1", result.getNomSprint());
        assertEquals(dateDebut, result.getDateDebut());
        assertEquals(dateFin, result.getDateFin());
    }

    @Test
    public void testConvertToSprint_WithNullInput() {
        Sprint result = sprintService.convertToSprint(null);

        assertNull(result);
    }
}