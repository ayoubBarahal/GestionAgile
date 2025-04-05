package project.gestionprojet.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gestionprojet.DTO.EpicDTO;
import project.gestionprojet.Service.EpicService;

import java.util.List;

@RestController
@RequestMapping("/api/epics")
public class EpicController {

    @Autowired
    private EpicService epicService;

    @PostMapping("/add")
    public ResponseEntity<EpicDTO> createEpic(@RequestBody EpicDTO epicDTO) {
        EpicDTO createdEpic = epicService.createEpic(epicDTO);
        return new ResponseEntity<>(createdEpic, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EpicDTO> updateEpic(@PathVariable int id, @RequestBody EpicDTO epicDTO) {
        EpicDTO updatedEpic = epicService.updateEpic(id, epicDTO);
        return ResponseEntity.ok(updatedEpic);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEpic(@PathVariable int id) {
        epicService.deleteEpic(id);
        return ResponseEntity.ok("Epic avec ID " + id + " supprimé avec succès.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<EpicDTO>> getAllEpics() {
        List<EpicDTO> epics = epicService.getAllEpics();
        return ResponseEntity.ok(epics);
    }

    @GetMapping("/productBacklog/{idProductBacklog}")
    public ResponseEntity<List<EpicDTO>> getEpicsByProductBacklog(@PathVariable int idProductBacklog) {
        List<EpicDTO> epics = epicService.findAllEpicByProductBacklog(idProductBacklog);
        return ResponseEntity.ok(epics);
    }
}
