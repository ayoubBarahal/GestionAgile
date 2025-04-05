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
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/addTask")
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO task = taskService.createTask(taskDTO);
        return ResponseEntity.ok().body(task);
    }

    @PutMapping("/updateTask/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable(name = "id") int idTask , @RequestBody TaskDTO taskDTO) {
        TaskDTO task = taskService.updateTask(idTask, taskDTO);
        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "id") int idTask) {
            taskService.deleteTask(idTask);
            return ResponseEntity.ok("Task " + idTask + " deleted successfully!");
    }

    @GetMapping("/AllTasksByUserStory/{idTask}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByUserStory(@PathVariable int idTask) {
        List<TaskDTO> tasks = taskService.getAllTasksByUserStory(idTask);
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/getStatus/{id}")
    public ResponseEntity<Status> getTaskStatus(@PathVariable(name = "id") int idTask) {
        Status status = taskService.getStatus(idTask);
        return ResponseEntity.ok().body(status);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable(name = "id") int idTask , @RequestBody Status newStatus) {
        Status status = taskService.updateStatus(idTask, newStatus);
        return ResponseEntity.ok().body(status);
    }

}
