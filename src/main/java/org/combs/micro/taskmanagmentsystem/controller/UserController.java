package org.combs.micro.taskmanagmentsystem.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.combs.micro.taskmanagmentsystem.dto.UserDTO;
import org.combs.micro.taskmanagmentsystem.entity.User;
import org.combs.micro.taskmanagmentsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User controller")
public class UserController {
    private final UserService userService;

    @Operation(
            description = "Получение пользоватлея по id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @Positive Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }@Operation(
            description = "Получение пользоватлея по email"
    )

    @GetMapping("/by-email")
    public ResponseEntity<User> getUserByEmail(@RequestParam(name = "email")
                                                   @Email(message = "Not valid email") String email){
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }

}
