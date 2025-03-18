package project.gestionprojet;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.gestionprojet.DTO.TaskDTO;
import project.gestionprojet.Entities.Status;
import project.gestionprojet.Entities.UserStory;
import project.gestionprojet.Service.TaskService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TaskTest {

//    @Autowired
//    private TaskService taskService;
//
//    private TaskDTO task;
//
//    @BeforeEach
//    void init(){
//        task = new TaskDTO(1,"task Title","task description", Status.ToDo,1);
//    }
//
//    @Test
//    void AddTask(){
//        TaskDTO savedTask = taskService.createTask(task);
//        assertNotNull(savedTask);
//    }
}
