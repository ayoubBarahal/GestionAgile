package project.gestionprojet;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import project.gestionprojet.DTO.EpicDTO;
import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Entities.SprintBacklog;
import project.gestionprojet.Repositories.EpicRepo;
import project.gestionprojet.Repositories.ProductBacklogRepo;
import project.gestionprojet.Repositories.SprintBacklogRepo;
import project.gestionprojet.ServiceImpl.EpicServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EpicServiceImplTest {

    @Mock
    private EpicRepo epicRepo;

    @Mock
    private ProductBacklogRepo productBacklogRepo;

    @Mock
    private SprintBacklogRepo sprintBacklogRepo;

    @InjectMocks
    private EpicServiceImpl epicService;

    private EpicDTO epicDTO;
    private Epic epic;
    private ProductBacklog productBacklog;
    private SprintBacklog sprintBacklog;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productBacklog = new ProductBacklog();
        productBacklog.setIdProductBacklog(1);
        productBacklog.setNom("Test Product Backlog");

        sprintBacklog = new SprintBacklog();
        sprintBacklog.setIdSprintBacklog(1);
        sprintBacklog.setNom("Test Sprint Backlog");

        epic = new Epic();
        epic.setIdEpic(1);
        epic.setTitre("Test Epic");
        epic.setDescription("Test Description");
        epic.setProductBacklog(productBacklog);
        epic.setSprintBacklogs(sprintBacklog);

        epicDTO = new EpicDTO();
        epicDTO.setIdEpic(1);
        epicDTO.setTitre("Test Epic");
        epicDTO.setDescription("Test Description");
        epicDTO.setIdProductBacklog(1);
        epicDTO.setIdSprintBacklog(1);
    }

    @Test
    void testCreateEpic_Success() {
        when(productBacklogRepo.findById(anyInt())).thenReturn(Optional.of(productBacklog));
        when(sprintBacklogRepo.findById(anyInt())).thenReturn(Optional.of(sprintBacklog));
        when(epicRepo.save(any(Epic.class))).thenReturn(epic);

        EpicDTO result = epicService.createEpic(epicDTO);

        assertNotNull(result);
        assertEquals(epicDTO.getTitre(), result.getTitre());
        assertEquals(epicDTO.getDescription(), result.getDescription());
        assertEquals(epicDTO.getIdProductBacklog(), result.getIdProductBacklog());
        assertEquals(epicDTO.getIdSprintBacklog(), result.getIdSprintBacklog());

        verify(productBacklogRepo).findById(epicDTO.getIdProductBacklog());
        verify(sprintBacklogRepo).findById(epicDTO.getIdSprintBacklog());
        verify(epicRepo).save(any(Epic.class));
    }

    @Test
    void testCreateEpic_NullEpic() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            epicService.createEpic(null);
        });

        assertEquals("Epic is null", exception.getMessage());

        verify(epicRepo, never()).save(any(Epic.class));
    }

    @Test
    void testUpdateEpic_Success() {
        when(epicRepo.findById(anyInt())).thenReturn(Optional.of(epic));
        when(productBacklogRepo.findById(anyInt())).thenReturn(Optional.of(productBacklog));
        when(epicRepo.save(any(Epic.class))).thenReturn(epic);

        EpicDTO updatedDTO = new EpicDTO();
        updatedDTO.setIdEpic(1);
        updatedDTO.setTitre("Updated Epic");
        updatedDTO.setDescription("Updated Description");
        updatedDTO.setIdProductBacklog(1);
        updatedDTO.setIdSprintBacklog(1);

        EpicDTO result = epicService.updateEpic(1, updatedDTO);

        assertNotNull(result);
        assertEquals(updatedDTO.getTitre(), result.getTitre());
        assertEquals(updatedDTO.getDescription(), result.getDescription());
        assertEquals(1, result.getIdEpic());

        verify(epicRepo).findById(1);
        verify(productBacklogRepo).findById(1);
        verify(epicRepo).save(any(Epic.class));
    }
//
//    @Test
//    void testUpdateEpic_NotFound() {
//        when(epicRepo.findById(anyInt())).thenReturn(Optional.empty());
//
//        EpicDTO updatedDTO = new EpicDTO();
//        updatedDTO.setIdEpic(1);
//        updatedDTO.setTitre("Updated Epic");
//        updatedDTO.setDescription("Updated Description");
//        updatedDTO.setIdProductBacklog(1);
//        updatedDTO.setIdSprintBacklog(1);
//
//        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
//            epicService.updateEpic(1, updatedDTO);
//        });
//
//        assertEquals("Epic not found", exception.getMessage());
//
//        verify(epicRepo).findById(1);
//        verify(epicRepo, never()).save(any(Epic.class));
//    }
//
//    @Test
//    void testDeleteEpic_Success() {
//        when(epicRepo.findById(anyInt())).thenReturn(Optional.of(epic));
//        doNothing().when(epicRepo).delete(any(Epic.class));
//
//        epicService.deleteEpic(1);
//
//        verify(epicRepo).findById(1);
//        verify(epicRepo).delete(epic);
//    }
//
//    @Test
//    void testDeleteEpic_NotFound() {
//        when(epicRepo.findById(anyInt())).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
//            epicService.deleteEpic(1);
//        });
//
//        assertEquals("Epic a supprimer est introuvable", exception.getMessage());
//
//        verify(epicRepo).findById(1);
//        verify(epicRepo, never()).delete(any(Epic.class));
//    }
//
//    @Test
//    void testFindAllEpicByProductBacklog() {
//        List<Epic> epics = new ArrayList<>();
//        epics.add(epic);
//
//        when(productBacklogRepo.findById(anyInt())).thenReturn(Optional.of(productBacklog));
//        when(epicRepo.findAllByProductBacklog(any(ProductBacklog.class))).thenReturn(epics);
//
//        List<EpicDTO> results = epicService.findAllEpicByProductBacklog(1);
//
//        assertNotNull(results);
//        assertEquals(1, results.size());
//        assertEquals(epic.getTitre(), results.get(0).getTitre());
//        assertEquals(epic.getDescription(), results.get(0).getDescription());
//
//        verify(productBacklogRepo).findById(1);
//        verify(epicRepo).findAllByProductBacklog(productBacklog);
//    }
//
//    @Test
//    void testGetAllEpics() {
//        List<Epic> epics = new ArrayList<>();
//        epics.add(epic);
//
//        when(epicRepo.findAll()).thenReturn(epics);
//
//        List<EpicDTO> results = epicService.getAllEpics();
//
//        assertNotNull(results);
//        assertEquals(1, results.size());
//        assertEquals(epic.getTitre(), results.get(0).getTitre());
//        assertEquals(epic.getDescription(), results.get(0).getDescription());
//
//        verify(epicRepo).findAll();
//    }
//
////    @Test
////    void testConvertToListDto() {
////        List<Epic> epics = new ArrayList<>();
////        epics.add(epic);
////
////        List<EpicDTO> results = epicService.convertToListDto(epics);
////
////        assertNotNull(results);
////        assertEquals(1, results.size());
////        assertEquals(epic.getTitre(), results.get(0).getTitre());
////        assertEquals(epic.getDescription(), results.get(0).getDescription());
////        assertEquals(epic.getIdEpic(), results.get(0).getIdEpic());
////        assertEquals(epic.getProductBacklog().getIdProductBacklog(), results.get(0).getIdProductBacklog());
////        assertEquals(epic.getSprintBacklogs().getIdSprintBacklog(), results.get(0).getIdSprintBacklog());
////    }
}