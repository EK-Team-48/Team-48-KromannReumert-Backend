package com.example.kromannreumert.todo.mapper;

import com.example.kromannreumert.todo.dto.ToDoResponseDto;
import com.example.kromannreumert.todo.dto.TodoRequestDto;
import com.example.kromannreumert.todo.entity.Status;
import com.example.kromannreumert.todo.entity.ToDo;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class ToDoMapper {

    public ToDo toToDo(TodoRequestDto todoRequestDto) {
        return new ToDo(
                todoRequestDto.name(),
                todoRequestDto.description(),
                LocalDateTime.now(),
                todoRequestDto.startDate(),
                todoRequestDto.endDate(),
                todoRequestDto.priority(),
                Status.NOT_STARTED,
                false
        );
    }

    public ToDoResponseDto toToDoResponseDto(ToDo toDo) {
        return new ToDoResponseDto(
                toDo.getId(),
                toDo.getName(),
                toDo.getDescription(),
                toDo.getCreated(),
                toDo.getStartDate(),
                toDo.getEndDate(),
                toDo.getUsers(),
                toDo.getArchived()
        );
    }
}
