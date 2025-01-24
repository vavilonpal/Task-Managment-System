package org.combs.micro.taskmanagmentsystem.service;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.combs.micro.taskmanagmentsystem.entity.User;
import org.combs.micro.taskmanagmentsystem.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    public Optional<User> getUserById(Long id){
        return repository.findById(id);
    }

    public Optional<User> getUserByEmail( String email){
        return repository.findUserByEmail(email);
    }
    public List<User> getAllUsers(){
        return repository.findAll();
    }
    public Optional<User> getCurrentUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findUserByEmail(email);
    }
    public boolean isExists(Long id){
        return repository.existsById(id);
    }


    public Page<User> getAllUsers(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
