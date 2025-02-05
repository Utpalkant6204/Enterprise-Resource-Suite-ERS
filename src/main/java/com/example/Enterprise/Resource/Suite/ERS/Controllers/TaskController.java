package com.example.Enterprise.Resource.Suite.ERS.Controllers;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.TaskDTO;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/task/api")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {
        try{
            return new ResponseEntity<>(taskService.createTask(taskDTO), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Something Went Wrong", HttpStatus.BAD_REQUEST);
        }
    }
}
