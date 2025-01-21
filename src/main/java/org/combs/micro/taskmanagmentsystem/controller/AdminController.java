package org.combs.micro.taskmanagmentsystem.controller;


import org.combs.micro.taskmanagmentsystem.entity.Task;
import org.combs.micro.taskmanagmentsystem.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Validated Task task){
     return ResponseEntity.ok(new Task());
    }
}