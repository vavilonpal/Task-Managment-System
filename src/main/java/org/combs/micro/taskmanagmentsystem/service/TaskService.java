package org.combs.micro.taskmanagmentsystem.service;


import lombok.RequiredArgsConstructor;
import org.combs.micro.taskmanagmentsystem.entity.Task;
import org.combs.micro.taskmanagmentsystem.entity.User;
import org.combs.micro.taskmanagmentsystem.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;

    public Optional<Task> getTaskById(Long id){
        return repository.findById(id);
    }
    public List<Task> getAllTasks(){
        return repository.findAll();
    }
    public Page<Task> getAllTasksByAuthor(User author, Pageable pageable){
        return repository.findAllByAuthor(author, pageable);
    }
    public Page<Task> getAllTasksByExecutor(User executor, Pageable pageable){
        return repository.findAllByExecutor(executor, pageable);
    }

    public void deleteTask(Task task){
        repository.delete(task);
    }

    public Task updateTask(Task task){
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
