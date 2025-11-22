package com.example.kromannreumert.securityFeature.dto;

import com.example.kromannreumert.securityFeature.entity.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public record JwtResponseDTO(String username, String token, List<String> role) {
}
