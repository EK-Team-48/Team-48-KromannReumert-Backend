package com.example.kromannreumert.client.DTO;

import com.example.kromannreumert.user.entity.User;

import java.util.Set;

public record CreateClientDTO(String clientName, Set<User> users, Long idPrefix) {
}
