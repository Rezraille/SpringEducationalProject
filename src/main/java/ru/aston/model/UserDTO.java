package ru.aston.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Schema(description = "Сущность пользователя, используемые в слое представления.")
public record UserDTO(
        @Schema(description = "Идентификатор пользователя", example = "1", minimum = "0", accessMode = Schema.AccessMode.READ_WRITE)
        @PositiveOrZero
        @Max(value = Integer.MAX_VALUE)
        Integer id,

        @Schema(description = "Имя пользователя", example = "Name", accessMode = Schema.AccessMode.READ_WRITE)
        @Pattern(regexp ="[\\p{L}]+")
        String name,

        @Schema(description = "Почта пользователя", example = "example_1@test.ru", accessMode = Schema.AccessMode.READ_WRITE)
        @Email
        String email,

        @Schema(description = "Возраст пользователя", example = "100", accessMode = Schema.AccessMode.READ_WRITE)
        @PositiveOrZero
        @Max(value = Integer.MAX_VALUE)
        Integer age,

        @Schema(description = "Дата и время создания пользователя", example = "2018-11-21T11:13:13.285", accessMode = Schema.AccessMode.READ_WRITE)
        LocalDateTime createdAt
)  {
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