package org.combs.micro.taskmanagmentsystem.auth;


import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.combs.micro.taskmanagmentsystem.dto.UserDTO;
import org.combs.micro.taskmanagmentsystem.entity.User;
import org.combs.micro.taskmanagmentsystem.enums.RoleType;
import org.combs.micro.taskmanagmentsystem.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationResponse signUp(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(RoleType.ROLE_USER)
                .build();
        userRepository.save(user);

        var accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return JwtAuthenticationResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    public JwtAuthenticationResponse authenticate(AuthenticationRequest request) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword());
        authManager.authenticate(usernamePasswordAuthenticationToken);

        //var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User by email:  " + request.getEmail() + ",  not found!!"));
        User user = (User) userDetailsService.loadUserByUsername(request.getEmail());

        var accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return JwtAuthenticationResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtAuthenticationResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtService.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtService.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final User user = userRepository.findUserByEmail(email)
                    .orElseThrow(() -> new AuthException("Пользователь не найден"));
            final String accessToken = jwtService.generateAccessToken(user);
            return new JwtAuthenticationResponse(accessToken, null);
        }
        return new JwtAuthenticationResponse(null, null);
    }

    public JwtAuthenticationResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtService.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtService.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final User user = userRepository.findUserByEmail(email)
                    .orElseThrow(() -> new AuthException("Пользователь не найден"));
            final String accessToken = jwtService.generateAccessToken(user);
            final String newRefreshToken = jwtService.generateRefreshToken(user);
            return new JwtAuthenticationResponse(accessToken, newRefreshToken);
        }
        throw new AuthException("Невалидный JWT токен");
    }



}
