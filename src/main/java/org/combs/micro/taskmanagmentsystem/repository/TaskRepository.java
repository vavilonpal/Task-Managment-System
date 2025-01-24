package org.combs.micro.taskmanagmentsystem.repository;


import org.combs.micro.taskmanagmentsystem.entity.Task;
import org.combs.micro.taskmanagmentsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByAuthorId(Long authorId, Pageable pageable);
    Page<Task> findAllByExecutorId(Long executorId, Pageable pageable);
    boolean existsByIdAndAuthorId(Long taskId, Long authorId);
}
