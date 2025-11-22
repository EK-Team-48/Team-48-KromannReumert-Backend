package com.example.kromannreumert.securityFeature.service;

import com.example.kromannreumert.securityFeature.JwtUtil.JwtGenerator;
import com.example.kromannreumert.securityFeature.dto.JwtResponseDTO;
import com.example.kromannreumert.securityFeature.dto.LoginDTO;
import com.example.kromannreumert.securityFeature.entity.Role;
import com.example.kromannreumert.securityFeature.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtGenerator jwtIssuer;

    public LoginService(AuthenticationManager authenticationManager, UserService userService, JwtGenerator jwtIssuer) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtIssuer = jwtIssuer;
    }

    public JwtResponseDTO login(LoginDTO loginRequest) throws Exception {

        // This is for spring security to handle username and password with bcrypt
        // as it does not retrieve roles from the DB, we have to do it manually
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(), loginRequest.password()
                )
        );

        // For us to retrieve the user roles, we have to retrieve the user object
        User user = userService.findUserByUsername(loginRequest.username());

        // Get the roles, extract it to a String list so it can be forwarded with the request
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getRoleName().toUpperCase())
                .toList();

        // Generate the JWT token
        String token = jwtIssuer.issueToken(user.getUsername(), roles);

        // Return the JWT token
        return new JwtResponseDTO(user.getUsername(), token, roles);
    }
}
