package com.example.kromannreumert.todo.dto;

import com.example.kromannreumert.todo.entity.Priority;
import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;

public record TodoRequestDto(
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        Priority priority
) {}
