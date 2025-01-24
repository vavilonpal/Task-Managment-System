package org.combs.micro.taskmanagmentsystem.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.combs.micro.taskmanagmentsystem.enums.TaskPriority;
import org.combs.micro.taskmanagmentsystem.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks", schema = "task_manage_sc")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotNull(message = "Please fill in this field")
    @Max(value = 255, message = "The title should not be longer than 255 characters.")
    private String title;

    @Column(name = "description", nullable = false)
    @NotNull(message = "Please fill in this field")
    private String description;

    @Column(name = "status", nullable = false)
    @NotNull(message = "Please set status of the task")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "priority", nullable = false)
    @NotNull(message = "Please set priority of the task")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
