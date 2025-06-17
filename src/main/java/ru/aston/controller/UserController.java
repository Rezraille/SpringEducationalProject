package ru.aston.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.controller.util.Util;
import ru.aston.model.UserDTO;
import ru.aston.service.UserService;
import ru.aston.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserService userService;
    private final Util util;

    public UserController(UserService userService, Util util) {
        this.userService = userService;
        this.util = util;
    }

    @GetMapping("read/id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        logger.info("getUserById() id = {}", id);
        if (util.isPositiveNumber(id)) {
            Optional<UserDTO> optionalUserDTO = userService.getUserById(id);
            return optionalUserDTO.isPresent() ? ResponseEntity.ok(optionalUserDTO.get()) : ResponseEntity.notFound().build();
        } else {
            logger.error("Ошибка неправильный id = {}", id);
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        logger.info("createUser() UserDTO  = {}", userDTO);

        if (util.isCorrectUserDTO(userDTO)) {
            Optional<UserDTO> optionalUserDTO = userService.createUser(userDTO);
            return optionalUserDTO.isPresent() ? ResponseEntity.ok(optionalUserDTO.get()) : ResponseEntity.notFound().build();
        } else {
            logger.error("Ошибка неправильный requestBody {}", userDTO);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("update/old-id/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        logger.info("updateUser() id = {}, userDTO = {}", id, userDTO);

        if (util.isPositiveNumber(id) && util.isCorrectUserDTO(userDTO)) {
            boolean isUpdate = userService.updateUser(userDTO, id);
            return ResponseEntity.ok(isUpdate);
        } else {
            logger.error("Ошибка id =  {}, userDto = {}", id, userDTO);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        logger.info("deleteUser() id = {}", id);

        if (util.isPositiveNumber(id)) {
            boolean isRemove = userService.deleteUserById(id);
            return ResponseEntity.ok(isRemove);
        } else {
            logger.error("Ошибка неверный id =  {}", id);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("findAll")
    public ResponseEntity<List> findAllUsers() {
        logger.info("findAll()");

        List<UserDTO> usersDto = userService.findAllUsers();
        return ResponseEntity.ok(usersDto);
    }
}
