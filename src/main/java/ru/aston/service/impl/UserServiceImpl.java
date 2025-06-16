package ru.aston.service.impl;


import ru.aston.dao.UserDao;
import ru.aston.entity.User;
import ru.aston.model.UserDTO;
import ru.aston.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;


    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<UserDTO> getUserById(Integer id) {
        logger.trace("getUserById() id = {}" , id);
        UserDTO userDTOFromDB = null;
        try {
            Optional<User> userOptional = userDao.findById(id);
            if (userOptional.isPresent()) {
                userDTOFromDB = convertToDTO(userOptional.get());
            }
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            logger.error("Ошибка чтения из базы данных. Операция отменена.", e);
        }
        return Optional.ofNullable(userDTOFromDB);
    }

    @Override
    public Optional<UserDTO> createUser(UserDTO userDto) {
        logger.trace("createUser() = {}" , userDto);
        UserDTO userDTOFromDB = null;
        try {

            User entity = User.builder().
                    id(userDto.id()).
                    name(userDto.name()).
                    email(userDto.email()).
                    age(userDto.age()).
                    createdAt(userDto.createdAt()).
                    build();
            userDTOFromDB = convertToDTO(userDao.save(entity));

        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            logger.error("Ошибка добавления в базу данных. Операция отменена.", e);
        }
        return Optional.ofNullable(userDTOFromDB);
    }

    @Override
    public boolean updateUser(final UserDTO newUser, int oldUserId) {
        logger.trace("updateUser() id = {} with {}",oldUserId, newUser);
        try {

            int sumOfUpdate = userDao.updateUserAndReturnCount(
                    newUser.id(), newUser.name(), newUser.age(), newUser.email(), newUser.createdAt(), oldUserId
            );
            return sumOfUpdate > 0 ? true : false;
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            logger.error("Ошибка обновления базы данных. Операция отменена.", e);
            return false;
        }
    }

    @Override
    public boolean deleteUserById(final Integer id) {

        logger.trace("deleteUserById() id = {}" , id);
        try {
            int sumOfDeleted = userDao.deleteAndReturnCount(id);
            return sumOfDeleted > 0 ? true : false;
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            logger.error("Ошибка удаления из базы данных. Операция отменена.", e);
            return false;
        }
    }


    @Override
    public List<UserDTO> findAllUsers() {
        logger.trace("findAllUsers()");
        List<UserDTO> users = new ArrayList<>();
        try {
            users = userDao.findAll().stream()
                    .map(entity -> convertToDTO(entity))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            logger.error("Ошибка вывода из базы данных. Операция отменена.", e);
        }
        return users;
    }


    private UserDTO convertToDTO(User entity) {
        return new UserDTO(entity.getId(), entity.getName(), entity.getEmail(), entity.getAge(), entity.getCreatedAt());
    }
}
