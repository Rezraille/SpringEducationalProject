package ru.aston.controller;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.aston.model.UserDTO;
import ru.aston.service.UserService;
import ru.aston.service.impl.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.hateoas.CollectionModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.EntityModel;


@Validated
@RestController
@Tag(name = "Users", description = "CRUD операции над пользователями.")
@RequestMapping("api/users")
@ApiResponse(
        responseCode = "200",
        description = "Операция успешна",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserController.class),
                examples = @ExampleObject(
                        value = """
                            {
                                "timestamp": "2023-11-21T11:13:13.285",
                                "status": 200,
                                "message": "OK",
                                "path": "api/users/{resource}/{action}"
                            }
                            """
                )
        )
)
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Чтение.", description = "Получить пользователя из таблицы Users.")
    @GetMapping("read/id/{id}")
    public ResponseEntity<EntityModel<UserDTO>> getUserById(
            @PositiveOrZero @PathVariable("id") @Parameter(description = "Идентификатор пользователя") Integer id
    ) {
        logger.info("getUserById() id = {}", id);

        Optional<UserDTO> optionalUser = userService.getUserById(id);
        EntityModel<UserDTO> model = toEntityModel(optionalUser.get());
        return optionalUser.isPresent() ? ResponseEntity.ok(model) : ResponseEntity.notFound().build();
    }


    @Operation(summary = "Добавление.", description = "Добавить пользователя в таблицу Users.")
    @PutMapping("create")
    public ResponseEntity<EntityModel<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("createUser() UserDTO  = {}", userDTO);

        UserDTO user = userService.createUser(userDTO).get();
        return ResponseEntity.ok(toEntityModel(user));
    }


    @Operation(summary = "Обновление.", description = "Обновить пользователя в таблице Users.")
    @PutMapping("update/old-id/{id}")
    public ResponseEntity<EntityModel<Map<String, Boolean>>> updateUser(
            @PositiveOrZero @PathVariable("id") @Parameter(description = "Идентификатор пользователя") Integer id,
            @Valid @RequestBody UserDTO userDTO
    ) {
        logger.info("updateUser() id = {}, userDTO = {}", id, userDTO);

        boolean isUpdate = userService.updateUser(userDTO, id);
        EntityModel<Map<String, Boolean>> model = toEntityModel(isUpdate);
        return ResponseEntity.ok(model);
    }


    @Operation(summary = "Удаление.", description = "Удалить пользователя из таблицы Users.")
    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<EntityModel<Map<String, Boolean>>> deleteUser(
            @PositiveOrZero @PathVariable("id") @Parameter(description = "Идентификатор пользователя") Integer id
    ) {
        logger.info("deleteUser() id = {}", id);

        boolean isRemove = userService.deleteUserById(id);
        EntityModel<Map<String, Boolean>> model = toEntityModel(isRemove);
        return ResponseEntity.ok(model);
    }


    @Operation(summary = "Прочитать всех.", description = "Получить список всех пользователей из таблицы Users.")
    @GetMapping("findAll")
    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> findAllUsers() {
        logger.info("findAll()");

        List<UserDTO> users = userService.findAllUsers();
        List<EntityModel<UserDTO>> usersModel = users.stream()
                .map(user -> toEntityModel(user))
                .toList();
        CollectionModel<EntityModel<UserDTO>> models = CollectionModel.of(usersModel);
        return ResponseEntity.ok(models);
    }

    private EntityModel<UserDTO> toEntityModel(UserDTO user) {
        Link[] links = createLinks(user);
        return EntityModel.of(user, links);
    }

    private EntityModel<Map<String, Boolean>> toEntityModel(Boolean isHappened) {
        Map<String, Boolean> response = Map.of("success", isHappened);
        UserDTO example = new UserDTO(1, "example", "example@mail.com", 11, LocalDateTime.now());
        Link[] links = createLinks(example);
        return EntityModel.of(response, links);
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Link[] createLinks(UserDTO user) {
        UserDTO example = new UserDTO(1, "example", "example@mail.com", 11, LocalDateTime.now());
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(user.id())).withSelfRel();
        Link createLink = linkTo(methodOn(UserController.class).createUser(example)).withRel("create");
        Link updateLink = linkTo(methodOn(UserController.class).updateUser(user.id(), example)).withRel("update");
        Link deleteLink = linkTo(methodOn(UserController.class).deleteUser(user.id())).withRel("delete");
        Link allEmployeesLink = linkTo(methodOn(UserController.class).findAllUsers()).withRel("all-users");
        return new Link[]{selfLink, createLink, updateLink, deleteLink, allEmployeesLink};
    }
}
