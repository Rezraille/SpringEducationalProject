package ru.aston.controller.util;

import org.springframework.stereotype.Component;
import ru.aston.model.UserDTO;

import java.util.regex.Pattern;

@Component
public class Util {

    public boolean isPositiveNumber(Integer number) {
        if (number == null) return false;
        return number >= 0;
    }

    private boolean isEmail(String str) {
        if (str == null) return false;
        String regex = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str.trim()).matches();

    }

    private boolean isName(String str) {
        if (str == null) return false;
        String regex = "[\\p{L}]+";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str.trim()).matches();
    }

    public boolean isCorrectUserDTO(UserDTO userDTO) {
        if (userDTO == null) return false;
        return isPositiveNumber(userDTO.id()) && isName(userDTO.name()) && isEmail(userDTO.email()) && isPositiveNumber(userDTO.age());
    }
}
