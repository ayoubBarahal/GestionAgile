//package project.gestionprojet;
//
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import project.gestionprojet.DTO.UserStoryDTO;
//import project.gestionprojet.Entities.Epic;
//import project.gestionprojet.Entities.Priority;
//import project.gestionprojet.Entities.Status;
//import project.gestionprojet.Entities.UserStory;
//import project.gestionprojet.Repositories.EpicRepo;
//import project.gestionprojet.Repositories.UserStoryRepo;
//import project.gestionprojet.ServiceImpl.UserStoryServiceImpl;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class UserStoryServiceImplTest {
//
//    @InjectMocks
//    private UserStoryServiceImpl userStoryService;
//
//    @Mock
//    private UserStoryRepo userStoryRepo;
//
//    @Mock
//    private EpicRepo epicRepo;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreateUserStory() {
//        // Given
//        UserStoryDTO dto = new UserStoryDTO(0, "Story 1", "Description", "Desire", "Goal", "Role", Priority.MustDo, Status.Done, 1);
//        Epic epic = new Epic();
//        epic.setIdEpic(1);
//
//        UserStory savedUserStory = new UserStory();
//        savedUserStory.setIdUserStory(1);
//        savedUserStory.setTitre("Story 1");
//        savedUserStory.setEpic(epic);
//
//        when(epicRepo.findById(1)).thenReturn(Optional.of(epic));
//        when(userStoryRepo.save(any(UserStory.class))).thenReturn(savedUserStory);
//
//        // When
//        UserStoryDTO result = userStoryService.createUserStory(dto);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("Story 1", result.getTitre());
//        assertEquals(1, result.getIdUserStory());
//    }
//
//    @Test
//    void testUpdateUserStory() {
//        UserStory existing = new UserStory();
//        existing.setIdUserStory(1);
//        existing.setTitre("Old Story");
//
//        UserStoryDTO dto = new UserStoryDTO(1, "Updated Story", "Description", "Desire", "Goal", "Role", Priority.MustDo, Status.Done, 0);
//        UserStory updated = new UserStory();
//        updated.setIdUserStory(1);
//        updated.setTitre("Updated Story");
//
//        when(userStoryRepo.findById(1)).thenReturn(Optional.of(existing));
//        when(userStoryRepo.save(any(UserStory.class))).thenReturn(updated);
//
//        UserStoryDTO result = userStoryService.updateUserStory(dto);
//
//        assertNotNull(result);
//        assertEquals("Updated Story", result.getTitre());
//    }
//
//    @Test
//    void testDeleteUserStory() {
//        UserStory userStory = new UserStory();
//        userStory.setIdUserStory(1);
//        userStory.setTitre("Story to Delete");
//
//        when(userStoryRepo.findById(1)).thenReturn(Optional.of(userStory));
//
//        UserStoryDTO dto = new UserStoryDTO();
//        dto.setIdUserStory(1);
//
//        UserStoryDTO result = userStoryService.deleteUserStory(dto);
//
//        assertNotNull(result);
//        assertEquals(1, result.getIdUserStory());
//        verify(userStoryRepo, times(1)).delete(userStory);
//    }
//
//    @Test
//    void testGetUserStoryById() {
//        UserStory userStory = new UserStory();
//        userStory.setIdUserStory(1);
//        userStory.setTitre("Test Story");
//
//        when(userStoryRepo.findById(1)).thenReturn(Optional.of(userStory));
//
//        UserStoryDTO result = userStoryService.getUserStoryById(1);
//
//        assertNotNull(result);
//        assertEquals("Test Story", result.getTitre());
//    }
//
//    @Test
//    void testGetUserStoryByUserName() {
//        UserStory userStory = new UserStory();
//        userStory.setTitre("Story Title");
//
//        when(userStoryRepo.findByTitre("Story Title")).thenReturn(userStory);
//
//        UserStoryDTO result = userStoryService.getUserStoryByUserName("Story Title");
//
//        assertNotNull(result);
//        assertEquals("Story Title", result.getTitre());
//    }
//
//    @Test
//    void testGetAllUserStories() {
//        UserStory story1 = new UserStory();
//        story1.setTitre("Story 1");
//        UserStory story2 = new UserStory();
//        story2.setTitre("Story 2");
//
//        when(userStoryRepo.findAll()).thenReturn(Arrays.asList(story1, story2));
//
//        List<UserStoryDTO> result = userStoryService.getAllUserStories();
//
//        assertEquals(2, result.size());
//        assertEquals("Story 1", result.get(0).getTitre());
//    }
//
//    @Test
//    void testGetUserStoriesByEpicId() {
//        UserStory story = new UserStory();
//        story.setTitre("Epic Story");
//
//        when(userStoryRepo.findByEpicIdEpic(1)).thenReturn(List.of(story));
//
//        List<UserStoryDTO> result = userStoryService.getUserStoriesByEpicId(1);
//
//        assertEquals(1, result.size());
//        assertEquals("Epic Story", result.get(0).getTitre());
//    }
//
//    @Test
//    void testGetUserStoriesByStatus() {
//        UserStory story = new UserStory();
//        story.setTitre("Done Story");
//        story.setStatus(Status.Done);
//
//        when(userStoryRepo.findByStatus(Status.Done)).thenReturn(List.of(story));
//
//        List<UserStoryDTO> result = userStoryService.getUserStoriesByStatus("DONE");
//
//        assertEquals(1, result.size());
//        assertEquals("Done Story", result.get(0).getTitre());
//    }
//
//    @Test
//    void testGetUserStoriesByPriority() {
//        UserStory story = new UserStory();
//        story.setTitre("High Priority Story");
//        story.setPriority(Priority.MustDo);
//
//        when(userStoryRepo.findByPriority(Priority.MustDo)).thenReturn(List.of(story));
//
//        List<UserStoryDTO> result = userStoryService.getUserStoriesByPriority("HIGH");
//
//        assertEquals(1, result.size());
//        assertEquals("High Priority Story", result.get(0).getTitre());
//    }
//}
