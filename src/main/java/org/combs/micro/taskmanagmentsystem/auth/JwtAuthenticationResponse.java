package org.combs.micro.taskmanagmentsystem.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
}
