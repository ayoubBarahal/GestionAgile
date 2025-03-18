package project.gestionprojet.ServiceImpl;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.DTO.ProjetDTO;
import project.gestionprojet.DTO.TaskDTO;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Entities.Status;
import project.gestionprojet.Entities.Task;
import project.gestionprojet.Entities.UserStory;
import project.gestionprojet.Repositories.TaskRepo;
import project.gestionprojet.Repositories.UserStoryRepo;
import project.gestionprojet.Service.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private UserStoryRepo userStoryRepo;

    @Override
    public TaskDTO createTask(TaskDTO task) {
        if (task==null){
            throw new IllegalArgumentException("task "+task.getTitle()+" Not Found !");
        }
        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setStatus(task.getStatus());
        UserStory userStory = userStoryRepo.findByIdUserStory(task.getIdUserStory());
        newTask.setUserStory(userStory);
        Task savedTask = taskRepo.save(newTask);
        return new TaskDTO(savedTask.getIdTask(), savedTask.getTitle(), savedTask.getDescription(), savedTask.getStatus(),savedTask.getUserStory().getIdUserStory());
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
        Task taskToDelete = taskRepo.findById(idTask).get();
        if (taskToDelete==null){
            throw new IllegalArgumentException("task "+idTask+" Not Found !");
        }
        taskRepo.delete(taskToDelete);
    }

    @Override
    public Status getStatus(int idTask) {
        Task task = taskRepo.findById(idTask).get();
        if (task==null){
           throw  new ResourceNotFoundException("task "+idTask+" Not Found !");
        }
        return task.getStatus();
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
        Task task = taskRepo.findById(idTask).get();
        if (task==null){
            throw new IllegalArgumentException("task "+idTask+" Not Found !");
        }
        task.setStatus(status);
        taskRepo.save(task);
        return status;
    }


}
