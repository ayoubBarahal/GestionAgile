package project.gestionprojet.ServiceImpl;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.DTO.TaskDTO;
import project.gestionprojet.Entities.Status;
import project.gestionprojet.Entities.Task;
import project.gestionprojet.Entities.UserStory;
import project.gestionprojet.Repositories.TaskRepo;
import project.gestionprojet.Repositories.UserStoryRepo;
import project.gestionprojet.Service.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private UserStoryRepo userStoryRepo;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        if (taskDTO == null) {
            throw new IllegalArgumentException("TaskDTO is null - Not Found");
        }

        UserStory userStory = userStoryRepo.findByIdUserStory(taskDTO.getIdUserStory());
        if (userStory == null) {
            throw new ResourceNotFoundException("UserStory with id " + taskDTO.getIdUserStory() + " Not Found");
        }

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setUserStory(userStory);

        Task savedTask = taskRepo.save(task);

        TaskDTO savedDTO = new TaskDTO();
        savedDTO.setIdTask(savedTask.getIdTask());
        savedDTO.setTitle(savedTask.getTitle());
        savedDTO.setDescription(savedTask.getDescription());
        savedDTO.setStatus(savedTask.getStatus());
        savedDTO.setIdUserStory(userStory.getIdUserStory());

        return savedDTO;
    }


    @Override
    public TaskDTO updateTask(int idTask, TaskDTO task) {
        Task taskToUpdate = taskRepo.findById(idTask).get();
        if (taskToUpdate==null){
            throw new IllegalArgumentException("task "+idTask+" Not Found !");
        }
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setStatus(task.getStatus());
        UserStory userStory = userStoryRepo.findByIdUserStory(task.getIdUserStory());
        taskToUpdate.setUserStory(userStory);
        Task savedTask = taskRepo.save(taskToUpdate);
        return new TaskDTO(savedTask.getIdTask(), savedTask.getTitle(), savedTask.getDescription(), savedTask.getStatus(),savedTask.getUserStory().getIdUserStory());
    }

    @Override
    public void deleteTask(int idTask) {

        Optional<Task> task = taskRepo.findById(idTask);
        if (task.isEmpty()){
            throw new IllegalArgumentException("task "+idTask+" Not Found !");
        }
        Task taskToDelete = task.get();
        taskRepo.delete(taskToDelete);
    }

    @Override
    public Status getStatus(int idTask) {
        Optional<Task> task = taskRepo.findById(idTask);

        if (task.isEmpty()){
            throw  new ResourceNotFoundException("task "+idTask+" Not Found !");
        }
        Task task1 = task.get();
        return task1.getStatus();
    }


    @Override
    public List<TaskDTO> getAllTasksByUserStory(int idUserStory) {
        UserStory userStory = userStoryRepo.findByIdUserStory(idUserStory);
        if (userStory==null){
            throw new ResourceNotFoundException("user story "+idUserStory+" Not Found !");
        }
        List<Task> tasks=taskRepo.findAllByUserStory(userStory);
        List<TaskDTO> taskDTOS = new ArrayList<>();

        if (tasks.isEmpty()){
            throw new ResourceNotFoundException(" No Tasks Found !");
        }
        for (Task task : tasks) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setTitle(task.getTitle());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setStatus(task.getStatus());
            taskDTO.setIdUserStory(task.getUserStory().getIdUserStory());
            taskDTOS.add(taskDTO);

        }
        return taskDTOS;
    }

    @Override
    public Status updateStatus(int idTask, Status status) {
        Optional<Task> task = taskRepo.findById(idTask);

        if (task.isEmpty()){
            throw new IllegalArgumentException("task "+idTask+" Not Found !");
        }
        Task task1 = task.get();
        task1.setStatus(status);
        taskRepo.save(task1);
        return status;
    }


}