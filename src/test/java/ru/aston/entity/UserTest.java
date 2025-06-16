package ru.aston.entity;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest {
    private final LocalDateTime now = LocalDateTime.now();
    private final User user = User.builder()
            .id(1)
            .name("Test")
            .email("test@example.com")
            .age(30)
            .createdAt(now)
            .build();

    @Test
    void equals_whenSameObject() {
        assertTrue(user.equals(user));
    }

    @Test
    void equals_whenNull() {
        assertFalse(user.equals(null));
    }

    @Test
    void equals_whenDifferentClass() {
        assertFalse(user.equals("Not a User object"));
    }

    @Test
    void equals_whenAllFieldsSame() {
        User other = User.builder()
                .id(1)
                .name("Test")
                .email("test@example.com")
                .age(30)
                .createdAt(now)
                .build();
        assertTrue(user.equals(other));
    }

    @Test
    void equals_whenThisIdNull() {
        User userOne = User.builder().id(null).build();
        User userTwo = User.builder().id(1).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOtherIdNull() {
        User userOne = User.builder().id(1).build();
        User userTwo = User.builder().id(null).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenBothIdNull() {
        User userOne = User.builder().id(null).build();
        User userTwo = User.builder().id(null).build();
        assertTrue(userOne.equals(userTwo));
    }

    @Test
    void equals_whenIdDifferent() {
        User userOne = User.builder().id(1).build();
        User userTwo = User.builder().id(2).build();
        assertFalse(userOne.equals(userTwo));
    }


    @Test
    void equals_whenThisNameNull() {
        User userOne = User.builder().name(null).build();
        User userTwo = User.builder().name("John").build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOtherNameNull() {
        User userOne = User.builder().name("John").build();
        User userTwo = User.builder().name(null).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenBothNamesNull() {
        User userOne = User.builder().name(null).build();
        User userTwo = User.builder().name(null).build();
        assertTrue(userOne.equals(userTwo));
    }

    @Test
    void equals_whenNamesDifferent() {
        User userOne = User.builder().name("John").build();
        User userTwo = User.builder().name("Alice").build();
        assertFalse(userOne.equals(userTwo));
    }


    @Test
    void equals_whenThisEmailNull() {
        User userOne = User.builder().email(null).build();
        User userTwo = User.builder().email("john@example.com").build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOtherEmailNull() {
        User userOne = User.builder().email("john@example.com").build();
        User userTwo = User.builder().email(null).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenBothEmailNull() {
        User userOne = User.builder().email(null).build();
        User userTwo = User.builder().email(null).build();
        assertTrue(userOne.equals(userTwo));
    }

    @Test
    void equals_whenEmailDifferent() {
        User userOne = User.builder().email("john@example.com").build();
        User userTwo = User.builder().email("alice@example.com").build();
        assertFalse(userOne.equals(userTwo));
    }


    @Test
    void equals_whenThisAgeNull() {
        User userOne = User.builder().age(null).build();
        User userTwo = User.builder().age(30).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOtherAgeNull() {
        User userOne = User.builder().age(30).build();
        User userTwo = User.builder().age(null).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenBothAgeNull() {
        User userOne = User.builder().age(null).build();
        User userTwo = User.builder().age(null).build();
        assertTrue(userOne.equals(userTwo));
    }

    @Test
    void equals_whenAgeDifferent() {
        User userOne = User.builder().age(30).build();
        User userTwo = User.builder().age(31).build();
        assertFalse(userOne.equals(userTwo));
    }


    @Test
    void equals_whenThisCreatedAtNull() {
        User userOne = User.builder().createdAt(null).build();
        User userTwo = User.builder().createdAt(now).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOtherCreatedAtNull() {
        User userOne = User.builder().createdAt(now).build();
        User userTwo = User.builder().createdAt(null).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenBothCreatedAtNull() {
        User userOne = User.builder().createdAt(null).build();
        User userTwo = User.builder().createdAt(null).build();
        assertTrue(userOne.equals(userTwo));
    }

    @Test
    void equals_whenCreatedAtDifferent() {
        User userOne = User.builder().createdAt(now).build();
        User userTwo = User.builder().createdAt(now.plusDays(1)).build();
        assertFalse(userOne.equals(userTwo));
    }


    @Test
    void equals_whenThisIdAndNameNull() {
        User userOne = User.builder().id(null).name(null).build();
        User userTwo = User.builder().id(1).name("Test").build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOtherIdAndNameNull() {
        User userOne = User.builder().id(1).name("Test").build();
        User userTwo = User.builder().id(null).name(null).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenThisEmailAndAgeNull() {
        User userOne = User.builder().email(null).age(null).build();
        User userTwo = User.builder().email("test@example.com").age(30).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOtherEmailAndAgeNull() {
        User userOne = User.builder().email("test@example.com").age(30).build();
        User userTwo = User.builder().email(null).age(null).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenThisCreatedAtAndNameNull() {
        User userOne = User.builder().createdAt(null).name(null).build();
        User userTwo = User.builder().createdAt(now).name("Test").build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOtherCreatedAtAndNameNull() {
        User userOne = User.builder().createdAt(now).name("Test").build();
        User userTwo = User.builder().createdAt(null).name(null).build();
        assertFalse(userOne.equals(userTwo));
    }


    @Test
    void equals_whenIdAndNameDifferent() {
        User userOne = User.builder().id(1).name("TestOne").build();
        User userTwo = User.builder().id(2).name("TestTwo").build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenEmailAndAgeDifferent() {
        User userOne = User.builder().email("test@example.com").age(30).build();
        User userTwo = User.builder().email("testTwo@example.com").age(31).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenCreatedAtAndNameDifferent() {
        User userOne = User.builder().createdAt(now).name("TestOne").build();
        User userTwo = User.builder().createdAt(now.plusDays(1)).name("TestTwo").build();
        assertFalse(userOne.equals(userTwo));
    }


    @Test
    void equals_whenOnlyIdDifferent() {
        User userOne = User.builder().id(1).name("Test").email("test@example.com").age(30).createdAt(now).build();
        User userTwo = User.builder().id(2).name("Test").email("test@example.com").age(30).createdAt(now).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOnlyNameDifferent() {
        User userOne = User.builder().id(1).name("TestOne").email("test@example.com").age(30).createdAt(now).build();
        User userTwo = User.builder().id(1).name("TestTwo").email("test@example.com").age(30).createdAt(now).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOnlyEmailDifferent() {
        User userOne = User.builder().id(1).name("Test").email("test@example.com").age(30).createdAt(now).build();
        User userTwo = User.builder().id(1).name("Test").email("testTwo@example.com").age(30).createdAt(now).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOnlyAgeDifferent() {
        User userOne = User.builder().id(1).name("Test").email("test@example.com").age(30).createdAt(now).build();
        User userTwo = User.builder().id(1).name("Test").email("test@example.com").age(31).createdAt(now).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOnlyCreatedAtDifferent() {
        User userOne = User.builder().id(1).name("Test").email("test@example.com").age(30).createdAt(now).build();
        User userTwo = User.builder().id(1).name("Test").email("test@example.com").age(30).createdAt(now.plusDays(1)).build();
        assertFalse(userOne.equals(userTwo));
    }


    @Test
    void equals_whenBothEmpty() {
        User userOne = new User();
        User userTwo = new User();
        assertTrue(userOne.equals(userTwo));
    }

    @Test
    void equals_whenOneEmptyOneNot() {
        User userOne = new User();
        User userTwo = User.builder().id(1).build();
        assertFalse(userOne.equals(userTwo));
    }


    @Test
    void equals_whenSomeFieldsNullSomeNot() {
        User userOne = User.builder().id(1).name(null).email("test@example.com").age(null).createdAt(now).build();
        User userTwo = User.builder().id(1).name("Test").email(null).age(30).createdAt(now).build();
        assertFalse(userOne.equals(userTwo));
    }

    @Test
    void equals_whenMixedNullAndNonNull() {
        User user1 = User.builder().id(null).name("Test").email(null).age(30).createdAt(null).build();
        User user2 = User.builder().id(1).name(null).email("test@example.com").age(null).createdAt(now).build();
        assertFalse(user1.equals(user2));
    }


    @Test
    void hashCode_whenConsistentWithEquals() {
        User user1 = User.builder()
                .id(1)
                .name("Test")
                .email("test@example.com")
                .age(30)
                .createdAt(now)
                .build();

        User user2 = User.builder()
                .id(1)
                .name("Test")
                .email("test@example.com")
                .age(30)
                .createdAt(now)
                .build();

        assertTrue(user1.equals(user2));
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void hashCode_DifferentForDifferentObjects() {
        User user1 = User.builder().id(1).build();
        User user2 = User.builder().id(2).build();
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}
