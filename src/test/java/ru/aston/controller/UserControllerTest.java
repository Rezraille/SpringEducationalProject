package ru.aston.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.aston.dao.UserDao;
import ru.aston.model.UserDTO;
import ru.aston.service.UserService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserControllerTest extends BaseIntegrationTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    public void cleanUp() {
        userDao.deleteAll();
    }

    @Test
    public void getUserById_whenUserExists_returnsUser() {
        UserDTO userDTO = new UserDTO(105, "testCreate", "create@test.tt", 105, timeNow());
        userService.createUser(userDTO);
        int id = userDTO.id();
        final ResponseEntity<UserDTO> response = restTemplate.getForEntity(
                String.format("http://localhost:%d/api/users/read/id/%d", port, id),
                UserDTO.class);
        userService.getUserById(id).ifPresentOrElse(
                user -> assertAll(
                        () -> assertTrue(105 == user.id()),
                        () -> assertEquals("testCreate", user.name()),
                        () -> assertEquals("create@test.tt", user.email()),
                        () -> assertTrue(105 == user.age()),
                        () -> assertEquals(user, response.getBody())
                ), () -> fail("User not found"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void createUser_whenUserExists_returnsCreatedUser() {
        UserDTO userDTO = new UserDTO(105, "testCreate", "create@test.tt", 105, timeNow());
        final HttpHeaders headers = new HttpHeaders();
        final ResponseEntity<UserDTO> response = restTemplate.exchange(
                String.format("http://localhost:%d/api/users/create", port),
                HttpMethod.PUT,
                new HttpEntity<>(userDTO, headers),
                UserDTO.class);

        userService.createUser(userDTO).ifPresentOrElse(
                user -> assertAll(
                        () -> assertTrue(105 == user.id()),
                        () -> assertEquals("testCreate", user.name()),
                        () -> assertEquals("create@test.tt", user.email()),
                        () -> assertTrue(105 == user.age())
                ), () -> fail("User not found"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



    @Test
    public void updateUser_whenUserExists_returnTrue() {
        UserDTO userDTO = new UserDTO(105, "testCreate", "create@test.tt", 105, timeNow());
        userService.createUser(userDTO);
        int oldId = userDTO.id();
        UserDTO newUserDTO = new UserDTO(100, "testUpdate", "update@test.tt", 100, timeNow());

        final HttpHeaders headers = new HttpHeaders();
        final ResponseEntity<Boolean> responseUpdate = restTemplate.exchange(
                String.format("http://localhost:%d/api/users/update/old-id/%d", port, oldId),
                HttpMethod.PUT,
                new HttpEntity<>(newUserDTO, headers),
                Boolean.class);

        assertEquals(HttpStatus.OK, responseUpdate.getStatusCode());
        assertTrue(responseUpdate.getBody());
        List<UserDTO> usersAfterUpdate = userService.findAllUsers();
        assertTrue(usersAfterUpdate.size() == 1);
        assertEquals(newUserDTO, usersAfterUpdate.get(0));
    }

    @Test
    public void updateUser_whenUserNotExists_returnFalse() {
        UserDTO userDTO = new UserDTO(105, "testCreate", "create@test.tt", 105, timeNow());
        userService.createUser(userDTO);
        int oldId = userDTO.id() + 1;
        UserDTO newUserDTO = new UserDTO(100, "testUpdate", "update@test.tt", 100, timeNow());

        final HttpHeaders headers = new HttpHeaders();
        final ResponseEntity<Boolean> responseUpdate = restTemplate.exchange(
                String.format("http://localhost:%d/api/users/update/old-id/%d", port, oldId),
                HttpMethod.PUT,
                new HttpEntity<>(newUserDTO, headers),
                Boolean.class);

        assertEquals(HttpStatus.OK, responseUpdate.getStatusCode());
        assertFalse(responseUpdate.getBody());
        List<UserDTO> usersAfterUpdate = userService.findAllUsers();
        assertTrue(usersAfterUpdate.size() == 1);
        assertEquals(userDTO, usersAfterUpdate.get(0));
    }

    @Test
    public void deleteUser_whenUserExists_returnTrue() {
        UserDTO userDTO = new UserDTO(105, "testCreate", "create@test.tt", 105, timeNow());
        userService.createUser(userDTO);
        int oldId = userDTO.id();

        final ResponseEntity<Boolean> response = restTemplate.exchange(
                String.format("http://localhost:%d/api/users/delete/id/%d", port, oldId),
                HttpMethod.DELETE,
                null,
                Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        assertTrue(userService.getUserById(105).isEmpty());

    }

    @Test
    public void deleteUser_whenUserNotExists_returnFalse() {
              final ResponseEntity<Boolean> response = restTemplate.exchange(
                String.format("http://localhost:%d/api/users/delete/id/%d", port, 1),
                HttpMethod.DELETE,
                null,
                Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
    }

    @Test
    public void findAllUsers_whenUserExists_returnsAllUsers() {
        int listSize = 3;
        UserDTO userOne = new UserDTO(1, "One", "find_1@test.tt", 1, timeNow());
        userService.createUser(userOne);
        UserDTO userTwo = new UserDTO(2, "Two", "find_2@test.tt", 2, timeNow());
        userService.createUser(userTwo);
        UserDTO userThree = new UserDTO(3, "Three", "find_3@test.tt", 3, timeNow());
        userService.createUser(userThree);

        final ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                String.format("http://localhost:%d/api/users/findAll", port), HttpMethod.GET,null,
                 new ParameterizedTypeReference<List<UserDTO>>() {
                });
        List<UserDTO> users = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(listSize == users.size());
        assertEquals(users.get(0), userOne);
        assertEquals(users.get(1), userTwo);
        assertEquals(users.get(2), userThree);
    }

    private LocalDateTime timeNow()
    {
        return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }



}
