package org.combs.micro.taskmanagmentsystem.controller;


import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.combs.micro.taskmanagmentsystem.auth.AuthenticationRequest;
import org.combs.micro.taskmanagmentsystem.auth.JwtAuthenticationResponse;
import org.combs.micro.taskmanagmentsystem.auth.AuthenticationService;
import org.combs.micro.taskmanagmentsystem.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Validated UserDTO userDTO,
                                        BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(authService.signUp(userDTO));

    }
    @GetMapping("/authenticate")
    public ResponseEntity<JwtAuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @PostMapping("token")
    public ResponseEntity<JwtAuthenticationResponse> getNewAccessToken(@RequestBody String refreshToken) throws AuthException {
        final JwtAuthenticationResponse token = authService.getAccessToken(refreshToken);
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtAuthenticationResponse> getNewRefreshToken(@RequestBody String request) throws AuthException {
        final JwtAuthenticationResponse token = authService.refresh(request);
        return ResponseEntity.ok(token);
    }


}
