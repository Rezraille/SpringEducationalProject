package ru.aston.controller.util;


import ru.aston.model.UserDTO;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    private final Util util = new Util();


    @Test
    public void isCorrectNumber_positiveNumber_returnsTrue() {
        assertTrue(util.isCorrectNumber(10));
    }

    @Test
    public void isCorrectNumber_zero_returnsTrue() {
        assertTrue(util.isCorrectNumber(0));
    }

    @Test
    public void isCorrectNumber_negativeNumber_returnsFalse() {
        assertFalse(util.isCorrectNumber(-5));
    }

    @Test
    public void isCorrectNumber_null_returnsFalse() {
        assertFalse(util.isCorrectNumber(null));
    }

    @Test
    public void isEmail_validEmails_returnsTrue() {
        UserDTO user = new UserDTO(1, "Name",   "1234567890@example.com", 25, LocalDateTime.now());
        assertTrue(util.isCorrectUserDTO(user));
    }

    @Test
    public void isEmail_invalidEmails_returnsFalse() {
        UserDTO user = new UserDTO(1, "Name", "email", 25,LocalDateTime.now());
        assertFalse(util.isCorrectUserDTO(user));
    }

    @Test
    public void isName_validNames_returnsTrue() {
        UserDTO user = new UserDTO(1, "name", "valid@example.com", 25,LocalDateTime.now());
        assertTrue(util.isCorrectUserDTO(user));
    }

    @Test
    public void isName_invalidNames_returnsFalse() {
        UserDTO user = new UserDTO(1, "123", "valid@example.com", 25,LocalDateTime.now());
        assertFalse(util.isCorrectUserDTO(user));
    }

    @Test
    void isCorrectUserDTO_allFieldsValid_returnsTrue() {
        UserDTO user = new UserDTO(1, "Name", "valid@example.com", 30,LocalDateTime.now());
        assertTrue(util.isCorrectUserDTO(user));
    }

    @Test
    void isCorrectUserDTO_invalidId_returnsFalse() {
        UserDTO user = new UserDTO(-1, "Name", "valid@example.com", 30,LocalDateTime.now());
        assertFalse(util.isCorrectUserDTO(user));
    }

    @Test
    void isCorrectUserDTO_invalidName_returnsFalse() {
        UserDTO user = new UserDTO(1, "123", "valid@example.com", 30,LocalDateTime.now());
        assertFalse(util.isCorrectUserDTO(user));
    }

    @Test
    void isCorrectUserDTO_invalidEmail_returnsFalse() {
        UserDTO user = new UserDTO(1, "Name", "invalid-email", 30,LocalDateTime.now());
        assertFalse(util.isCorrectUserDTO(user));
    }

    @Test
    void isCorrectUserDTO_invalidAge_returnsFalse() {
        UserDTO user = new UserDTO(1, "Name", "valid@example.com", -5,LocalDateTime.now());
        assertFalse(util.isCorrectUserDTO(user));
    }

}