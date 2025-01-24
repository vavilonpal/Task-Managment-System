package org.combs.micro.taskmanagmentsystem.request;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.combs.micro.taskmanagmentsystem.entity.User;
import org.combs.micro.taskmanagmentsystem.enums.TaskPriority;
import org.combs.micro.taskmanagmentsystem.enums.TaskStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    @NotNull(message = "Please fill in this field")
    @Max(value = 255, message = "The title should not be longer than 255 characters.")
    private String title;
    @NotNull(message = "Please fill in this field")
    private String description;
    @NotNull(message = "Please set status of the task")
    private TaskStatus status;
    @NotNull(message = "Please set priority of the task")
    private TaskPriority priority;
    private User author;
    private User executor;


    private LocalDateTime createdAt = LocalDateTime.now();
}
