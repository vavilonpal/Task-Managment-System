package org.combs.micro.taskmanagmentsystem.controller;


import lombok.RequiredArgsConstructor;
import org.combs.micro.taskmanagmentsystem.entity.Task;
import org.combs.micro.taskmanagmentsystem.entity.User;
import org.combs.micro.taskmanagmentsystem.service.TaskService;
import org.combs.micro.taskmanagmentsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/vi/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/by-executor/{executor_id}")
    public ResponseEntity<?> getTasksByExecutor(@PathVariable(name = "executor_id") Long executorId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sortBy,
                                                @RequestParam(defaultValue = "asc") String sortDirection) {

        User executor = userService.getUserById(executorId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Executor not found"));

        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Task> tasks = taskService.getAllTasksByExecutor(executor, pageable);
        if (tasks.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/by-author/{author_id}")
    public ResponseEntity<?> getTasksByAuthor(@PathVariable(name = "author_id") Long authorId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sortBy,
                                              @RequestParam(defaultValue = "asc") String sortDirection) {

        User author = userService.getUserById(authorId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Task> tasks = taskService.getAllTasksByAuthor(author, pageable);
        if (tasks.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tasks);
    }


}
