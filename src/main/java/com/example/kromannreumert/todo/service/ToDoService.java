package com.example.kromannreumert.todo.service;

import com.example.kromannreumert.todo.entity.ToDo;
import com.example.kromannreumert.todo.repository.ToDoRepository;

import java.util.List;
import java.util.Optional;

public class ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDo> findAll() {
        return toDoRepository.findAll();
    }

    public Optional<ToDo> findToDoById(Long id) {
        return toDoRepository.findById(id);
    }

    public void save(ToDo toDo) {
        toDoRepository.save(toDo);
    }
}
