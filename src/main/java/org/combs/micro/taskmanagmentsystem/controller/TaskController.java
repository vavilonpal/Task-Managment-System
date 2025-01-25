package org.combs.micro.taskmanagmentsystem.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.combs.micro.taskmanagmentsystem.entity.Task;
import org.combs.micro.taskmanagmentsystem.entity.User;
import org.combs.micro.taskmanagmentsystem.request.TaskRequest;
import org.combs.micro.taskmanagmentsystem.response.TaskResponse;
import org.combs.micro.taskmanagmentsystem.service.PaginationAndSortingService;
import org.combs.micro.taskmanagmentsystem.service.TaskService;
import org.combs.micro.taskmanagmentsystem.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Controller", description = "Контроллер c CRUD тасков. Позволяет поулчать таски аутентифицированного пользователя и поулчать таски по автору и исполнителю")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final PaginationAndSortingService paginationAndSortingService;

    @Operation(
            summary = "Получение созданных пользователем тасков",
            description = "Получает пользователя и  находит созданные им задачи"
    )
    @GetMapping("/my-tasks")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<TaskResponse>> getMyTasks(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                 @RequestParam(defaultValue = "asc") String sortDirection) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Pageable pageable = paginationAndSortingService.createTaskPageable(page, size, sortBy, sortDirection);

        Page<TaskResponse> tasks = taskService.getAllTasksByAuthorId(user.getId(), pageable).map(taskService::mapToResponse);

        return ResponseEntity.ok(tasks);
    }
    @Operation(
            summary ="Создание задачи",
            description = "Позволяет создать задачу"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskRequest taskRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(taskRequest.getStatus())
                .priority(taskRequest.getPriority())
                .createdAt(taskRequest.getCreatedAt())
                .author(user)
                .executor(taskRequest.getExecutor())
                .build();

        taskService.createTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }
    @Operation(
            summary ="Получение задач по исполнителю",
            description = "Позволяет получать задачи по id исполнителя"
    )
    @GetMapping("/by-executor/{executor_id}")
    public ResponseEntity<Page<TaskResponse>> getTasksByExecutor(@PathVariable(name = "executor_id") Long executorId,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                                 @RequestParam(defaultValue = "asc") String sortDirection) {

        if (!(userService.isExists(executorId))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Pageable pageable = paginationAndSortingService.createTaskPageable(page, size, sortBy, sortDirection);
        Page<TaskResponse> taskResponses = taskService.getAllTasksByExecutorId(executorId, pageable)
                .map(taskService::mapToResponse);
        return ResponseEntity.ok(taskResponses);
    }
    @Operation(
            summary ="Получение задач по автору",
            description = "Позволяет получать задачи по id автора"
    )
    @GetMapping("/by-author/{author_id}")
    public ResponseEntity<?> getTasksByAuthor(
            @PathVariable(name = "author_id") Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        if (!(userService.isExists(authorId))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Pageable pageable = paginationAndSortingService.createTaskPageable(page, size, sortBy, sortDirection);

        Page<TaskResponse> taskResponses = taskService.getAllTasksByAuthorId(authorId, pageable)
                .map(taskService::mapToResponse);

        return ResponseEntity.ok(taskResponses);
    }

    @Operation(
            summary ="Удаление задачи",
            description = "Позволяет авторизованному ползьвотелю удалять созданную им задачу"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "taskId") Long taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (!(taskService.existsByIdAndAuthorId(taskId, user.getId()))) {
            return ResponseEntity.badRequest().body("Task not found or has't acces to this operation");
        }

        taskService.deleteTask(taskId);
        return ResponseEntity.ok(String.format("Task with %s successfully deleted", taskId));
    }
    @Operation(
            summary ="Обновление задачи",
            description = "Позволяет авторизованному пользователю обновить созданную им задачу"
    )
//    @SecurityRequirement(name = "JWT")
    @PutMapping
    public ResponseEntity<?> updateTask(@RequestBody @Valid Task task, BindingResult bindingResult) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (!(taskService.existsByIdAndAuthorId(task.getId(), user.getId()))) {
            return ResponseEntity.badRequest().body("Task not found or has't acces to this operation");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Task changedTask = taskService.updateTask(task);
        return ResponseEntity.ok(changedTask);
    }


}
