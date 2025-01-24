package org.combs.micro.taskmanagmentsystem.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.combs.micro.taskmanagmentsystem.entity.Comment;
import org.combs.micro.taskmanagmentsystem.enums.TaskPriority;
import org.combs.micro.taskmanagmentsystem.enums.TaskStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private List<Comment> comments;
    private String createdAt;
}
