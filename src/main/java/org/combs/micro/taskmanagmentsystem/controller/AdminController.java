package org.combs.micro.taskmanagmentsystem.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.combs.micro.taskmanagmentsystem.entity.Task;
import org.combs.micro.taskmanagmentsystem.entity.User;
import org.combs.micro.taskmanagmentsystem.service.PaginationAndSortingService;
import org.combs.micro.taskmanagmentsystem.service.TaskService;
import org.combs.micro.taskmanagmentsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "Admin controller")
public class AdminController {
    private final TaskService taskService;
    private final UserService userService;
    private final PaginationAndSortingService paginationAndSortingService;

    @Operation(
            summary = "Получение всех задач",
            description = "Адимн имеет доступ к получению всех задач"
    )
    @GetMapping("/tasks")
    public ResponseEntity<Page<Task>> getMyTasks(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(defaultValue = "id") String sortBy,
                                                         @RequestParam(defaultValue = "asc") String sortDirection) {

        Pageable pageable = paginationAndSortingService.createTaskPageable(page, size, sortBy, sortDirection);
        Page<Task> tasks = taskService.getAllTasks(pageable);

        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary ="Удаление задачи",
            description = "Позволяет админу удалить любую задачу"
    )
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "taskId") Long taskId) {

        taskService.deleteTask(taskId);
        return ResponseEntity.ok(String.format("Task with %s successfully deleted", taskId));
    }

    @Operation(
            summary ="Обновление задачи",
            description = "Позволяет авторизованному пользователю обновить созданную им задачу"
    )
    @PutMapping
    public ResponseEntity<?> updateTask(@RequestBody @Valid Task task, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Task changedTask = taskService.updateTask(task);
        return ResponseEntity.ok(changedTask);
    }

    @Operation(
            description = "Получение всех пользователей",
            summary = "Возврщает всех пользоватлей, по "

    )
    @GetMapping("/users")
    public ResponseEntity<Page<User>> getUsers(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "id") String sortBy,
                                               @RequestParam(defaultValue = "asc") String sortDirection){

        Pageable pageable = paginationAndSortingService.createUserPageable(page, size, sortBy, sortDirection);
        Page<User> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);

    }
}
