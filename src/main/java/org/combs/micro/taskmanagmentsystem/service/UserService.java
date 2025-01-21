package org.combs.micro.taskmanagmentsystem.service;

import lombok.RequiredArgsConstructor;
import org.combs.micro.taskmanagmentsystem.entity.User;
import org.combs.micro.taskmanagmentsystem.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Optional<User> getUserById(Long id){
        return repository.findById(id);
    }
}
