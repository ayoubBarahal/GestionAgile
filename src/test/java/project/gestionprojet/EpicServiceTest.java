package project.gestionprojet;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.gestionprojet.DTO.EpicDTO;
import project.gestionprojet.Entities.Epic;
import project.gestionprojet.Entities.ProductBacklog;
import project.gestionprojet.Repositories.EpicRepo;
import project.gestionprojet.Repositories.ProductBacklogRepo;
import project.gestionprojet.ServiceImpl.EpicServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EpicServiceTest {

    @Mock
    private EpicRepo epicRepo;

    @Mock
    private ProductBacklogRepo productBacklogRepo;

    @InjectMocks
    private EpicServiceImpl epicService;

    private Epic epic;
    private EpicDTO epicDTO;
    private ProductBacklog productBacklog;

    @BeforeEach
    void setUp() {
        epic = new Epic();
        epic.setIdEpic(1);
        epic.setTitre("Epic Test");
        epic.setDescription("Test description");

        epicDTO = new EpicDTO();
        epicDTO.setIdEpic(1);
        epicDTO.setTitre("Epic Test");
        epicDTO.setDescription("Test description");
        epicDTO.setIdProductBacklog(1);

        productBacklog = new ProductBacklog();
        productBacklog.setIdProductBacklog(1);
    }

    @Test
    void testCreateEpic() {
        when(productBacklogRepo.findById(1)).thenReturn(productBacklog);
        when(epicRepo.save(any(Epic.class))).thenReturn(epic);

        EpicDTO result = epicService.createEpic(epicDTO);

        assertNotNull(result);
        assertEquals(epic.getIdEpic(), result.getIdEpic());
        verify(epicRepo, times(1)).save(any(Epic.class));
    }

    @Test
    void testUpdateEpic() {
        when(epicRepo.findById(1)).thenReturn(Optional.of(epic));
        when(productBacklogRepo.findById(1)).thenReturn(productBacklog);
        when(epicRepo.save(any(Epic.class))).thenReturn(epic);

        EpicDTO result = epicService.updateEpic(1, epicDTO);

        assertNotNull(result);
        assertEquals("Epic Test", result.getTitre());
        verify(epicRepo, times(1)).save(any(Epic.class));
    }

    @Test
    void testUpdateEpicNotFound() {
        when(epicRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> epicService.updateEpic(1, epicDTO));
    }

    @Test
    void testDeleteEpic() {
        when(epicRepo.findById(1)).thenReturn(Optional.of(epic));
        doNothing().when(epicRepo).delete(any(Epic.class));

        assertDoesNotThrow(() -> epicService.deleteEpic(1));
        verify(epicRepo, times(1)).delete(epic);
    }

    @Test
    void testDeleteEpicNotFound() {
        when(epicRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> epicService.deleteEpic(1));
    }

    @Test
    void testFindAllEpics() {
        when(epicRepo.findAll()).thenReturn(List.of(epic));

        List<EpicDTO> result = epicService.getAllEpics();

        assertEquals(1, result.size());
        assertEquals("Epic Test", result.get(0).getTitre());
        verify(epicRepo, times(1)).findAll();
    }
}
