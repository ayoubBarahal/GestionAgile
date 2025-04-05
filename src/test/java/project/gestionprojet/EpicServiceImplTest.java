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

import java.util.Arrays;
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

    @InjectMocks
    private EpicServiceImpl epicService;

    private EpicDTO epicDTO;
    private Epic epic;
    private ProductBacklog productBacklog;

    @BeforeEach
    void setUp() {
        // Initialiser les données de test
        // Nous gardons idSprintBacklog dans le DTO même si nous ne l'utilisons pas dans le service
        epicDTO = new EpicDTO(1, "Test Epic", "Test Description", 1, 2);

        productBacklog = new ProductBacklog();
        productBacklog.setIdProductBacklog(1);

        epic = new Epic();
        epic.setIdEpic(1);
        epic.setTitre("Test Epic");
        epic.setDescription("Test Description");
        epic.setProductBacklog(productBacklog);
    }

    @Test
    void testCreateEpic() {
        // Arrange
        when(productBacklogRepo.findById(anyInt())).thenReturn(Optional.ofNullable(productBacklog));
        when(epicRepo.save(any(Epic.class))).thenReturn(epic);

        // Act
        EpicDTO result = epicService.createEpic(epicDTO);

        // Assert
        assertNotNull(result);
        assertEquals(epicDTO.getIdEpic(), result.getIdEpic());
        assertEquals(epicDTO.getTitre(), result.getTitre());
        assertEquals(epicDTO.getDescription(), result.getDescription());
        assertEquals(epicDTO.getIdProductBacklog(), epicDTO.getIdProductBacklog());
        // Nous ne testons pas idSprintBacklog puisqu'il n'est pas utilisé dans le service
        verify(epicRepo, times(1)).save(any(Epic.class));
    }


//    @Test
//    void testCreateEpicWithNullDTO() {
//        // Act & Assert
//        assertThrows(EntityNotFoundException.class, () -> epicService.createEpic(null));
//        verify(epicRepo, never()).save(any(Epic.class));
//    }

    @Test
    void testUpdateEpic() {
        // Arrange
        when(epicRepo.findById(anyInt())).thenReturn(Optional.of(epic));
        when(productBacklogRepo.findById(anyInt())).thenReturn(Optional.ofNullable(productBacklog));
        when(epicRepo.save(any(Epic.class))).thenReturn(epic);

        // Act
        EpicDTO result = epicService.updateEpic(1, epicDTO);

        // Assert
        assertNotNull(result);
        assertEquals(epicDTO.getTitre(), result.getTitre());
        assertEquals(epicDTO.getDescription(), result.getDescription());
        verify(epicRepo, times(1)).save(any(Epic.class));
    }

    @Test
    void testUpdateEpicNotFound() {
        // Arrange
        when(epicRepo.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> epicService.updateEpic(1, epicDTO));
        verify(epicRepo, never()).save(any(Epic.class));
    }

    @Test
    void testDeleteEpic() {
        // Arrange
        when(epicRepo.findById(anyInt())).thenReturn(Optional.of(epic));

        // Act
        epicService.deleteEpic(1);

        // Assert
        verify(epicRepo, times(1)).delete(any(Epic.class));
    }

    @Test
    void testDeleteEpicNotFound() {
        // Arrange
        when(epicRepo.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> epicService.deleteEpic(1));
        verify(epicRepo, never()).delete(any(Epic.class));
    }

    @Test
    void testFindAllEpicByProductBacklog() {
        // Arrange
        List<Epic> epicList = Arrays.asList(epic);
        when(productBacklogRepo.findById(anyInt())).thenReturn(Optional.ofNullable(productBacklog));
        when(epicRepo.findAllByProductBacklog(any(ProductBacklog.class))).thenReturn(epicList);

        // Act
        List<EpicDTO> result = epicService.findAllEpicByProductBacklog(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(epic.getTitre(), result.get(0).getTitre());
        assertEquals(epic.getDescription(), result.get(0).getDescription());
    }

    @Test
    void testGetAllEpics() {
        // Arrange
        List<Epic> epicList = Arrays.asList(epic);
        when(epicRepo.findAll()).thenReturn(epicList);

        // Act
        List<EpicDTO> result = epicService.getAllEpics();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(epic.getTitre(), result.get(0).getTitre());
        assertEquals(epic.getDescription(), result.get(0).getDescription());
    }

    @Test
    void testConvertToListDto() {
        // Arrange
        List<Epic> epicList = Arrays.asList(epic);

        // Act
        List<EpicDTO> result = epicService.convertToListDto(epicList);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(epic.getTitre(), result.get(0).getTitre());
        assertEquals(epic.getDescription(), result.get(0).getDescription());
        assertEquals(epic.getIdEpic(), result.get(0).getIdEpic());
        // Nous ne testons pas idSprintBacklog car il n'est pas défini dans la méthode convertToListDto
    }

}