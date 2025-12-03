package com.example.kromannreumert.todo.controller;

import com.example.kromannreumert.todo.dto.ToDoRequestDto;
import com.example.kromannreumert.todo.dto.ToDoRequestNewToDoDto;
import com.example.kromannreumert.todo.dto.ToDoResponseDto;
import com.example.kromannreumert.todo.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    /**
     * Retrieve all todos belonging to the authenticated user.
     *
     * @param principal the authenticated user's security principal; the principal's name is used to identify the user
     * @return a ResponseEntity containing the list of ToDoResponseDto with HTTP 200 on success, or an empty response with HTTP 500 if a runtime error occurs
     */
    @GetMapping("/todos")
    public ResponseEntity<List<ToDoResponseDto>> findAll(Principal principal) {
        try {
            List<ToDoResponseDto> responseDtos = toDoService.findAll(principal.getName());
            return ResponseEntity.ok(responseDtos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieve todos assigned to the authenticated user.
     *
     * @param principal the security principal whose name identifies the user
     * @return a ResponseEntity whose body is a list of ToDoResponseDto assigned to the principal;
     *         on success the response has HTTP 200 and the list as the body, on server error it has
     *         no body and HTTP 500
     */
    @GetMapping("/todos/assigned")
    public ResponseEntity<List<ToDoResponseDto>> findAssigned(Principal principal) {
        try {
            List<ToDoResponseDto> responseDtos = toDoService.findAssignedToUser(principal.getName());
            return ResponseEntity.ok(responseDtos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieve a specific to-do by id for the authenticated user.
     *
     * @param id        the id of the to-do to retrieve
     * @param principal the authenticated user principal
     * @return          HTTP 200 with the ToDoResponseDto when found, HTTP 404 when not found
     */
    @GetMapping("/todos/{id}")
    public ResponseEntity<ToDoResponseDto> findById(@PathVariable Long id, Principal principal) {
        try {
            ToDoResponseDto responseDto = toDoService.findToDoById(principal.getName(),id);
            return ResponseEntity.ok(responseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<ToDoResponseDto> createToDo(@RequestBody ToDoRequestNewToDoDto todoRequestDto, Principal principal) {
        try {
            ToDoResponseDto responseDto = toDoService.createToDo(principal.getName(), todoRequestDto);
            URI location = URI.create("/todos" + responseDto.id());
            return ResponseEntity.created(location).body(responseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id, Principal principal) {
        try {
            toDoService.deleteTodo(principal.getName(),id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<ToDoResponseDto> updateTodo(@PathVariable Long id, @RequestBody ToDoRequestDto todoRequestDto, Principal principal) {
        try {
            ToDoResponseDto toDoResponseDto = toDoService.updateTodo(id, principal.getName(), todoRequestDto);
            return ResponseEntity.ok(toDoResponseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/todos/size")
    public ResponseEntity<Integer> getToDoSize() {
        try {
            Integer toDoSize = toDoService.getToDoSize();
            return ResponseEntity.ok(toDoSize);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }
    }
}