package ru.aston.dao;

import ru.aston.entity.User;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users WHERE id = :id", nativeQuery = true)
    Integer deleteAndReturnCount(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET id = :newId, name = :newName, age = :newAge, email = :newEmail, " +
            "created_at = :newCreatedAt WHERE id = :oldId", nativeQuery = true)
    Integer updateUserAndReturnCount(
            @Param("newId") Integer newId, @Param("newName") String newName,
            @Param("newAge") Integer newAge, @Param("newEmail") String newEmail,
            @Param("newCreatedAt")LocalDateTime newCreatedAt,  @Param("oldId") Integer oldId
    );
}
