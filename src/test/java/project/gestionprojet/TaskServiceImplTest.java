package project.gestionprojet;



import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.gestionprojet.DTO.TaskDTO;
import project.gestionprojet.Entities.Status;
import project.gestionprojet.Entities.Task;
import project.gestionprojet.Entities.UserStory;
import project.gestionprojet.Repositories.TaskRepo;
import project.gestionprojet.Repositories.UserStoryRepo;
import project.gestionprojet.ServiceImpl.TaskServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepo taskRepo;

    @Mock
    private UserStoryRepo userStoryRepo;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDTO taskDTO;
    private UserStory userStory;

    @BeforeEach
    void setUp() {
        userStory = new UserStory();
        userStory.setIdUserStory(1);

        task = new Task();
        task.setIdTask(1);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(Status.ToDo);
        task.setUserStory(userStory);

        taskDTO = new TaskDTO();
        taskDTO.setIdTask(1);
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Test Description");
        taskDTO.setStatus(Status.ToDo);
        taskDTO.setIdUserStory(1);
    }

    @Test
    void testCreateTask_Success() {
        when(userStoryRepo.findByIdUserStory(anyInt())).thenReturn(userStory);
        when(taskRepo.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.createTask(taskDTO);

        assertNotNull(result);
        assertEquals(taskDTO.getTitle(), result.getTitle());
        assertEquals(taskDTO.getDescription(), result.getDescription());
        assertEquals(taskDTO.getStatus(), result.getStatus());
        assertEquals(taskDTO.getIdUserStory(), result.getIdUserStory());

        verify(userStoryRepo, times(1)).findByIdUserStory(anyInt());
        verify(taskRepo, times(1)).save(any(Task.class));
    }

    @Test
    void testCreateTask_NullTask() {
        taskDTO = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(taskDTO);
        });

        assertTrue(exception.getMessage().contains("Not Found"));
    }

    @Test
    void testUpdateTask_Success() {
        when(taskRepo.findById(anyInt())).thenReturn(Optional.of(task));
        when(userStoryRepo.findByIdUserStory(anyInt())).thenReturn(userStory);
        when(taskRepo.save(any(Task.class))).thenReturn(task);

        taskDTO.setTitle("Updated Title");
        taskDTO.setDescription("Updated Description");
        taskDTO.setStatus(Status.InProgress);

        TaskDTO result = taskService.updateTask(1, taskDTO);

        assertNotNull(result);
        assertEquals(taskDTO.getTitle(), result.getTitle());
        assertEquals(taskDTO.getDescription(), result.getDescription());
        assertEquals(taskDTO.getStatus(), result.getStatus());
        assertEquals(taskDTO.getIdUserStory(), result.getIdUserStory());

        verify(taskRepo, times(1)).findById(anyInt());
        verify(userStoryRepo, times(1)).findByIdUserStory(anyInt());
        verify(taskRepo, times(1)).save(any(Task.class));
    }

    @Test
    void testDeleteTask_Success() {
        when(taskRepo.findById(anyInt())).thenReturn(Optional.of(task));

        taskService.deleteTask(1);

        verify(taskRepo, times(1)).findById(anyInt());
        verify(taskRepo, times(1)).delete(any(Task.class));
    }

    @Test
    void testGetStatus_Success() {
        when(taskRepo.findById(anyInt())).thenReturn(Optional.of(task));

        Status result = taskService.getStatus(1);

        assertEquals(Status.ToDo, result);
        verify(taskRepo, times(1)).findById(anyInt());
    }

    @Test
    void testGetAllTasksByUserStory_Success() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        when(userStoryRepo.findByIdUserStory(anyInt())).thenReturn(userStory);
        when(taskRepo.findAllByUserStory(any(UserStory.class))).thenReturn(tasks);

        List<TaskDTO> result = taskService.getAllTasksByUserStory(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(task.getTitle(), result.get(0).getTitle());
        assertEquals(task.getDescription(), result.get(0).getDescription());
        assertEquals(task.getStatus(), result.get(0).getStatus());
        assertEquals(task.getUserStory().getIdUserStory(), result.get(0).getIdUserStory());

        verify(userStoryRepo, times(1)).findByIdUserStory(anyInt());
        verify(taskRepo, times(1)).findAllByUserStory(any(UserStory.class));
    }

    @Test
    void testGetAllTasksByUserStory_EmptyList() {
        List<Task> emptyList = new ArrayList<>();

        when(userStoryRepo.findByIdUserStory(anyInt())).thenReturn(userStory);
        when(taskRepo.findAllByUserStory(any(UserStory.class))).thenReturn(emptyList);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getAllTasksByUserStory(1);
        });

        assertTrue(exception.getMessage().contains("No Tasks Found"));

        verify(userStoryRepo, times(1)).findByIdUserStory(anyInt());
        verify(taskRepo, times(1)).findAllByUserStory(any(UserStory.class));
    }

    @Test
    void testGetAllTasksByUserStory_UserStoryNotFound() {
        when(userStoryRepo.findByIdUserStory(anyInt())).thenReturn(null);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getAllTasksByUserStory(1);
        });

        assertTrue(exception.getMessage().contains("user story"));
        assertTrue(exception.getMessage().contains("Not Found"));

        verify(userStoryRepo, times(1)).findByIdUserStory(anyInt());
        verify(taskRepo, never()).findAllByUserStory(any(UserStory.class));
    }


}