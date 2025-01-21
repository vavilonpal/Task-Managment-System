package org.combs.micro.taskmanagmentsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.combs.micro.taskmanagmentsystem.enums.RoleType;

import java.util.HashSet;
import java.util.Set;

@Table(name = "roles",schema = "task_manage_sc")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RoleType type;

    @ManyToMany(mappedBy = "roles")
    Set<User> users = new HashSet<>();
}
