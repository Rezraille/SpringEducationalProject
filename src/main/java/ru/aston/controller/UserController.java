package ru.aston.controller;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import ru.aston.controller.util.Util;
import ru.aston.model.UserDTO;
import ru.aston.service.UserService;
import org.springframework.http.ResponseEntity;
import ru.aston.service.impl.UserServiceImpl;


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
        logger.trace("getUserById() id = {}", id);
        try {
            Optional<UserDTO> optionalUserDTO = Optional.empty();
            if (util.isCorrectNumber(id)) {
                optionalUserDTO = userService.getUserById(id);
            }
            return optionalUserDTO.isPresent() ? ResponseEntity.ok(optionalUserDTO.get()) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Ошибка BAD_REQUEST 400: \n", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        logger.trace("createUser() UserDTO  = {}", userDTO);
        try {
            Optional<UserDTO> optionalUserDTO = Optional.empty();
            if (util.isCorrectUserDTO(userDTO)) {
                optionalUserDTO = userService.createUser(userDTO);
            }
            return optionalUserDTO.isPresent() ? ResponseEntity.ok(optionalUserDTO.get()) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Ошибка BAD_REQUEST 400: \n", e);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("update/old-id/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        logger.trace("updateUser() id = {}, userDTO = {}", id, userDTO);
        boolean isUpdate = false;
        try {
            if (util.isCorrectNumber(id) && util.isCorrectUserDTO(userDTO)) {
                isUpdate = userService.updateUser(userDTO, id);
            }
            return ResponseEntity.ok(isUpdate);
        } catch (Exception e) {
            logger.error("Ошибка BAD_REQUEST 400: \n", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        logger.trace("deleteUser() id = {}", id);
        try {
            boolean isRemove = false;
            if (util.isCorrectNumber(id)) {
                isRemove = userService.deleteUserById(id);
            }
            return ResponseEntity.ok(isRemove);
        } catch (Exception e) {
            logger.error("Ошибка BAD_REQUEST 400: \n", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("findAll")
    public ResponseEntity<List> findAllUsers() {
        logger.trace("findAll()");
        try {
            List<UserDTO> usersDto = userService.findAllUsers();
            return ResponseEntity.ok(usersDto);
        } catch (Exception e) {
            logger.error("Ошибка BAD_REQUEST 400: \n", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
