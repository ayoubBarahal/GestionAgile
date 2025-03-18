package project.gestionprojet.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.gestionprojet.DTO.TaskDTO;
import project.gestionprojet.Entities.Status;
import project.gestionprojet.Service.TaskService;

import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/api/addTask")
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO task = taskService.createTask(taskDTO);
        return ResponseEntity.ok().body(task);
    }

    @PutMapping("/api/updateTask/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable(name = "id") int idTask , @RequestBody TaskDTO taskDTO) {
        TaskDTO task = taskService.updateTask(idTask, taskDTO);
        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/api/deleteTask/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "id") int idTask) {
            taskService.deleteTask(idTask);
            return ResponseEntity.ok("Task " + idTask + " deleted successfully!");
    }

    @GetMapping("/api/AllTasksByUserStory/{idTask}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByUserStory(@PathVariable int idTask) {
        List<TaskDTO> tasks = taskService.getAllTasksByUserStory(idTask);
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/api/getStatus/{id}")
    public ResponseEntity<Status> getTaskStatus(@PathVariable(name = "id") int idTask) {
        Status status = taskService.getStatus(idTask);
        return ResponseEntity.ok().body(status);
    }

    @PutMapping("/api/updateStatus/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable(name = "id") int idTask , @RequestBody Status newStatus) {
        Status status = taskService.updateStatus(idTask, newStatus);
        return ResponseEntity.ok().body(status);
    }

}
