package org.combs.micro.taskmanagmentsystem.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.combs.micro.taskmanagmentsystem.enums.RoleType;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Email(message = "Not correct email")
    private String email;

    @Size(min = 8, message = "Password must be min 8 characters length")
    private String password;
}
