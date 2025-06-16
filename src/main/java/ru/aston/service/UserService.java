package ru.aston.service;


import ru.aston.model.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getUserById(final Integer id);
    Optional<UserDTO> createUser(final UserDTO user);
    boolean updateUser(final UserDTO user, int oldUserId);
    boolean deleteUserById(final Integer id);
    List<UserDTO> findAllUsers();
}
