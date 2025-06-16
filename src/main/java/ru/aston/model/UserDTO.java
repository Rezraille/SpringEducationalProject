package ru.aston.model;

import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public record UserDTO(Integer id, String name, String email, Integer age, LocalDateTime createdAt) {
    @Transient
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");

    @Override
    public String toString() {
        return "User: " +
                "id = " + id +
                "; name = " + name +
                "; email = " + email +
                "; age = " + age +
                "; createdAt = " + createdAt.format(formatter) +
                '.';
    }
}
