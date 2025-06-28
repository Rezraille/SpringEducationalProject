package ru.aston.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.aston.dao.UserDao;
import ru.aston.entity.User;
import ru.aston.kafka.Command;
import ru.aston.kafka.Producer;
import ru.aston.model.UserDTO;
import ru.aston.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    private final Producer producer;


    public UserServiceImpl(UserDao userDao,Producer producer) {
        this.userDao = userDao;
        this.producer = producer;
    }

    @Override
    public Optional<UserDTO> getUserById(Integer id) {
        logger.info("getUserById() id = {}", id);

        UserDTO userDTOFromDB = null;
        Optional<User> userOptional = userDao.findById(id);
        if (userOptional.isPresent()) {
            userDTOFromDB = convertToDTO(userOptional.get());
        }
        return Optional.ofNullable(userDTOFromDB);
    }

    @Override
    public Optional<UserDTO> createUser(UserDTO userDto) {
        logger.info("createUser() = {}", userDto);

        User entity = User.builder().
                id(userDto.id()).
                name(userDto.name()).
                email(userDto.email()).
                age(userDto.age()).
                createdAt(userDto.createdAt()).
                build();
        UserDTO userDTOFromDB = convertToDTO(userDao.save(entity));
        if (userDto != null)
        {
            producer.sendMessage("USERS", Command.CREATE.name(), userDto.email());
        }
        return Optional.ofNullable(userDTOFromDB);
    }

    @Override
    public boolean updateUser(final UserDTO newUser, int oldUserId) {
        logger.info("updateUser() id = {} with {}", oldUserId, newUser);

        int sumOfUpdate = userDao.updateUserAndReturnCount(
                newUser.id(), newUser.name(), newUser.age(), newUser.email(), newUser.createdAt(), oldUserId
        );
        return sumOfUpdate > 0;
    }

    @Override
    public boolean deleteUserById(final Integer id) {
        logger.info("deleteUserById() id = {}", id);

        Optional<User> user = userDao.findById(id);
        if (user.isEmpty())
        {
            return false;
        }
        int sumOfDeleted = userDao.deleteAndReturnCount(id);
        if (sumOfDeleted > 0)
        {
            producer.sendMessage("USERS", Command.DELETE.name(), user.get().getEmail());
        }
        return sumOfDeleted > 0;
    }


    @Override
    public List<UserDTO> findAllUsers() {
        logger.info("findAllUsers()");

        List<UserDTO> users = new ArrayList<>();
        users = userDao.findAll().stream()
                .map(entity -> convertToDTO(entity))
                .collect(Collectors.toList());
        return users;
    }


    private UserDTO convertToDTO(User entity) {
        return new UserDTO(entity.getId(), entity.getName(), entity.getEmail(), entity.getAge(), entity.getCreatedAt());
    }
}
