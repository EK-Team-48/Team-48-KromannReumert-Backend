package com.example.kromannreumert.todo.controller;

import com.example.kromannreumert.todo.dto.ToDoResponseDto;
import com.example.kromannreumert.todo.service.ToDoService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("*")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ToDoResponseDto>> findAll(Principal principal) {
        try {
            List<ToDoResponseDto> responseDtos = toDoService.findAll(principal.getName());
            return new ResponseEntity<>(responseDtos, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
