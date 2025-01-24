package org.combs.micro.taskmanagmentsystem.service;


import lombok.RequiredArgsConstructor;
import org.combs.micro.taskmanagmentsystem.entity.Task;
import org.combs.micro.taskmanagmentsystem.entity.User;
import org.combs.micro.taskmanagmentsystem.repository.TaskRepository;
import org.combs.micro.taskmanagmentsystem.request.TaskRequest;
import org.combs.micro.taskmanagmentsystem.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    public TaskResponse mapToResponse(Task task){

        return TaskResponse.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .comments(task.getComments())
                .build();

    }
    public Task mapRequestToTask(TaskRequest request){
        return Task
                .builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .author(request.getAuthor())
                .executor(request.getExecutor())
                .createdAt(request.getCreatedAt())
                .build();
    }

    public Optional<Task> getTaskById(Long id) {
        return repository.findById(id);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Page<Task> getAllTasks(Pageable pageable) {
        return repository.findAll(pageable);
    }



    public Page<Task> getAllTasksByAuthorId(Long authorId, Pageable pageable) {
        return repository.findAllByAuthorId(authorId, pageable);
    }

    public Page<Task> getAllTasksByExecutorId(Long executorId, Pageable pageable) {
        return repository.findAllByExecutorId(executorId, pageable);
    }

    public void deleteTask(Long id) {
        if (!(repository.existsById(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        repository.deleteById(id);
    }

    public boolean existsByIdAndAuthorId(Long taskId, Long authorId){
        return repository.existsByIdAndAuthorId(taskId,authorId);
    }
    public Task createTask(Task task){
        return repository.save(task);
    }
    public Task updateTask(Task task) {
        Task updatetTask = Task.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .comments(task.getComments())
                .author(task.getAuthor())
                .executor(task.getExecutor())
                .build();
        return repository.save(task);
    }
}
