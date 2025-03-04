package project.gestionprojet.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gestionprojet.Entities.Projet;
import project.gestionprojet.Entities.Task;
import project.gestionprojet.Entities.UserStory;
import project.gestionprojet.Repositories.TaskRepo;
import project.gestionprojet.Service.TaskService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepo taskRepo ;

    @Override
    public Task createTask(Task task) {
        if(task!=null){
            return taskRepo.save(task);
        }
        return null;
    }

    @Override
    public Task updateTask(int idTask ,Task task) {
        boolean exist = taskRepo.existsById(task.getIdTask());
        if (!exist) {
            throw new IllegalStateException("Task n'existe pas");
        }
        else {
            Optional<Task> taskToUpdate = taskRepo.findById(idTask);
            if (taskToUpdate.isPresent()) {
                taskToUpdate.get().setDescription(task.getDescription());
                taskToUpdate.get().setTitle(task.getTitle());
                taskToUpdate.get().setStatus(task.getStatus());

            }
            assert taskToUpdate.isPresent() :new IllegalStateException("projet n'est pas mis a jour correctement");
            return taskRepo.save(taskToUpdate.get());
        }
    }

    @Override
    public void deleteTask(Task task) {
        Optional<Task> taskToDelete = taskRepo.findById(task.getIdTask());
        if (taskToDelete.isPresent()) {
            taskRepo.delete(taskToDelete.get());
        }
        else
            throw new IllegalStateException("Task n'existe pas");
    }

    @Override
    public List<Task> getTasks() {
        return taskRepo.findAll();
    }

    @Override
    public List<Task> getTasksByUserStory(UserStory userStory) {
        return taskRepo.findAllByUserStory(userStory) ;
    }

}
