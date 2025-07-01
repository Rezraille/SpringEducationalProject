package ru.aston.exeption;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private record ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String path,
            String message
    ) {}


    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(
            responseCode = "404",
            description = "Сущность не найдена",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                            {
                                "timestamp": "2023-11-21T11:13:13.285",
                                "status": 404,
                                "error": "Not Found",
                                "path": "/api/users/read/id/1",
                                "message": "User not found with id: 1"
                            }
                            """
                    )
            )
    )
    public ResponseEntity<Object> handleUserNotFoundException(Exception exception,
                                                                     HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        String id = path.substring(path.lastIndexOf("/") + 1);
        logger.error("Пользователя с id {} не существует. Операция отменена. ", id, exception);

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                path,
                exception.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }


    @ExceptionHandler({EntityExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(
            responseCode = "409",
            description = "Сущность уже существует",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
                            {
                                "timestamp": "2023-11-21T11:13:13.285",
                                "status": 409,
                                "error": "Conflict",
                                "path": "/api/users/create",
                                "message": "User already exist"
                            }
                            """
                    )
            )
    )
    public ResponseEntity<Object> handleUserAlreadyExistsException(EntityExistsException exception,
                                                                   HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        logger.error("Пользователь уже существует. Операция отменена. ",  exception);

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                path,
                exception.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }


    @ExceptionHandler({PersistenceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(
            responseCode = "500",
            description = "Ошибка c базой данных",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            description = "Ошибка может возникнуть при любом URL.",
                            value = """
                            {
                                "timestamp": "2023-11-21T11:13:13.285",
                                "status": 500,
                                "error": "Internal Server Error",
                                "path": "/api/{resource}/{action}",
                                "message": "Database error"
                            }
                            """
                    )
            )
    )
    public ResponseEntity<Object> handlePersistenceException(PersistenceException exception,
                                                             HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        logger.error("Ошибка c базой данных. Операция отменена.", exception);

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                path,
                exception.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "400",
            description = "Ошибка валидации",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            description = "Ошибка может возникнуть при любом URL.",
                            value = """
                            {
                                "timestamp": "2023-11-21T11:13:13.285",
                                "status": 400,
                                "error": "Bad Request",
                                "path": "/api/{resource}/{action}",
                                "message": "Database error"
                            }
                            """
                    )
            )
    )
    public ResponseEntity<Object> handleValidationException(ValidationException exception,
                                                            HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        logger.error("Ошибка. Ошибка валидации.", exception);

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                path,
                exception.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponse(
            responseCode = "500",
            description = "Ошибка c базой данных",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            description = "Ошибка может возникнуть при любом URL.",
                            value = """
                            {
                                "timestamp": "2023-11-21T11:13:13.285",
                                "status": 500,
                                "error": "Internal Server Error",
                                "path": "/api/{resource}/{action}",
                                "message": "Other error"
                            }
                            """
                    )
            )
    )
    public ResponseEntity<Object> handleDifferentException(RuntimeException exception,
                                                           HttpServletRequest request
    ) {
        String path = request.getRequestURI();
        logger.error("Ошибка. Операция отменена. ", exception);

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                path,
                exception.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}