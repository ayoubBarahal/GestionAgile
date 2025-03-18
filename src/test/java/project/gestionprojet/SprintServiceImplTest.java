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

        // Initialisation des dates
        dateDebut = new Date();
        dateFin = new Date(dateDebut.getTime() + 1000 * 60 * 60 * 24 * 14); // 14 jours plus tard

        // Initialisation du SprintBacklog
        sprintBacklog = new SprintBacklog();
        sprintBacklog.setIdSprintBacklog(1);
        sprintBacklog.setNom("Backlog Test");
        sprintBacklog.setDescription("Description du backlog de test");

        // Initialisation du Sprint
        sprint = new Sprint();
        sprint.setIdSprint(1);
        sprint.setNomSprint("Sprint 1");
        sprint.setDateDebut(dateDebut);
        sprint.setDateFin(dateFin);
        sprint.setSprintBacklog(sprintBacklog);

        // Initialisation du SprintDTO
        sprintDTO = new SprintDTO();
        sprintDTO.setIdSprint(1);
        sprintDTO.setNomSprint("Sprint 1");
        sprintDTO.setDateDebut(dateDebut);
        sprintDTO.setDateFin(dateFin);
        sprintDTO.setIdSprintBacklog(1);
    }

    @Test
    public void testAddSprint() {
        // Configuration du mock
        when(sprintRepo.save(any(Sprint.class))).thenReturn(sprint);

        // Création d'un DTO d'entrée
        SprintDTO inputDTO = new SprintDTO();
        inputDTO.setNomSprint("Sprint 1");
        inputDTO.setDateDebut(dateDebut);
        inputDTO.setDateFin(dateFin);
        inputDTO.setIdSprintBacklog(1);

        // Exécution de la méthode à tester
        SprintDTO result = sprintService.addSprint(inputDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals("Sprint 1", result.getNomSprint());
        assertEquals(dateDebut, result.getDateDebut());
        assertEquals(dateFin, result.getDateFin());
        assertEquals(1, result.getIdSprintBacklog());
        verify(sprintRepo, times(1)).save(any(Sprint.class));
    }

    @Test
    public void testUpdateSprint_WhenSprintExists() {
        // Configuration du mock
        when(sprintRepo.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintRepo.save(any(Sprint.class))).thenReturn(sprint);

        // Création du DTO pour la mise à jour
        SprintDTO updateDTO = new SprintDTO();
        updateDTO.setIdSprint(1);
        updateDTO.setNomSprint("Sprint 1 Updated");
        updateDTO.setDateDebut(dateDebut);
        updateDTO.setDateFin(dateFin);
        updateDTO.setIdSprintBacklog(1);

        // Exécution de la méthode à tester
        SprintDTO result = sprintService.updateSprint(updateDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        verify(sprintRepo, times(1)).findById(1);
        verify(sprintRepo, times(1)).save(any(Sprint.class));
    }

    @Test
    public void testUpdateSprint_WhenSprintDoesNotExist() {
        // Configuration du mock
        when(sprintRepo.findById(999)).thenReturn(Optional.empty());

        // Création du DTO pour la mise à jour
        SprintDTO updateDTO = new SprintDTO();
        updateDTO.setIdSprint(999);
        updateDTO.setNomSprint("Non-Existing Sprint");

        // Exécution de la méthode à tester
        SprintDTO result = sprintService.updateSprint(updateDTO);

        // Vérifications
        assertNull(result);
        verify(sprintRepo, times(1)).findById(999);
        verify(sprintRepo, never()).save(any(Sprint.class));
    }

    @Test
    public void testDeleteSprint_WhenSprintExists() {
        // Configuration du mock
        when(sprintRepo.findById(1)).thenReturn(Optional.of(sprint));
        doNothing().when(sprintRepo).delete(sprint);

        // Création du DTO pour la suppression
        SprintDTO deleteDTO = new SprintDTO();
        deleteDTO.setIdSprint(1);

        // Exécution de la méthode à tester
        SprintDTO result = sprintService.deleteSprint(deleteDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        assertEquals("Sprint 1", result.getNomSprint());
        verify(sprintRepo, times(1)).findById(1);
        verify(sprintRepo, times(1)).delete(sprint);
    }

    @Test
    public void testDeleteSprint_WhenSprintDoesNotExist() {
        // Configuration du mock
        when(sprintRepo.findById(999)).thenReturn(Optional.empty());

        // Création du DTO pour la suppression
        SprintDTO deleteDTO = new SprintDTO();
        deleteDTO.setIdSprint(999);

        // Exécution de la méthode à tester
        SprintDTO result = sprintService.deleteSprint(deleteDTO);

        // Vérifications
        assertNull(result);
        verify(sprintRepo, times(1)).findById(999);
        verify(sprintRepo, never()).delete(any(Sprint.class));
    }

    @Test
    public void testGetAllSprint() {
        // Préparation d'un deuxième sprint pour le test
        Sprint sprint2 = new Sprint();
        sprint2.setIdSprint(2);
        sprint2.setNomSprint("Sprint 2");
        sprint2.setDateDebut(dateDebut);
        sprint2.setDateFin(dateFin);
        sprint2.setSprintBacklog(sprintBacklog);

        // Configuration du mock
        when(sprintRepo.findAll()).thenReturn(Arrays.asList(sprint, sprint2));

        // Exécution de la méthode à tester
        List<SprintDTO> results = sprintService.getAllSprint();

        // Vérifications
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Sprint 1", results.get(0).getNomSprint());
        assertEquals("Sprint 2", results.get(1).getNomSprint());
        verify(sprintRepo, times(1)).findAll();
    }

    @Test
    public void testGetSprintById_WhenSprintExists() {
        // Configuration du mock
        when(sprintRepo.findById(1)).thenReturn(Optional.of(sprint));

        // Exécution de la méthode à tester
        SprintDTO result = sprintService.getSprintById(1);

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        assertEquals("Sprint 1", result.getNomSprint());
        verify(sprintRepo, times(1)).findById(1);
    }

    @Test
    public void testGetSprintById_WhenSprintDoesNotExist() {
        // Configuration du mock
        when(sprintRepo.findById(999)).thenReturn(Optional.empty());

        // Exécution de la méthode à tester
        SprintDTO result = sprintService.getSprintById(999);

        // Vérifications
        assertNull(result);
        verify(sprintRepo, times(1)).findById(999);
    }

    @Test
    public void testGetSprintByName_WhenSprintExists() {
        // Configuration du mock
        when(sprintRepo.findByNomSprint("Sprint 1")).thenReturn(sprint);

        // Exécution de la méthode à tester
        SprintDTO result = sprintService.getSprintByName("Sprint 1");

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        assertEquals("Sprint 1", result.getNomSprint());
        verify(sprintRepo, times(1)).findByNomSprint("Sprint 1");
    }

    @Test
    public void testGetSprintByName_WhenSprintDoesNotExist() {
        // Configuration du mock
        when(sprintRepo.findByNomSprint("Non-Existing Sprint")).thenReturn(null);

        // Exécution de la méthode à tester
        SprintDTO result = sprintService.getSprintByName("Non-Existing Sprint");

        // Vérifications
        assertNull(result);
        verify(sprintRepo, times(1)).findByNomSprint("Non-Existing Sprint");
    }

    @Test
    public void testConvertToSprintDTO() {
        // Exécution de la méthode à tester
        SprintDTO result = sprintService.convertToSprintDTO(sprint);

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        assertEquals("Sprint 1", result.getNomSprint());
        assertEquals(dateDebut, result.getDateDebut());
        assertEquals(dateFin, result.getDateFin());
        assertEquals(1, result.getIdSprintBacklog());
    }

    @Test
    public void testConvertToSprint() {
        // Exécution de la méthode à tester
        Sprint result = sprintService.convertToSprint(sprintDTO);

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.getIdSprint());
        assertEquals("Sprint 1", result.getNomSprint());
        assertEquals(dateDebut, result.getDateDebut());
        assertEquals(dateFin, result.getDateFin());
        // Notez que le SprintBacklog n'est pas défini dans la méthode convertToSprint
    }

    @Test
    public void testConvertToSprint_WithNullInput() {
        // Exécution de la méthode à tester
        Sprint result = sprintService.convertToSprint(null);

        // Vérifications
        assertNull(result);
    }
}