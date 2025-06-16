package ru.aston.controller.util;

import org.springframework.stereotype.Component;
import ru.aston.model.UserDTO;

import java.util.regex.Pattern;

@Component
public class Util {

    public boolean isCorrectNumber(Integer number) {
        try {
            return number >= 0 ? true : false;
        } catch (Exception exception) {
            return false;
        }
    }

    private boolean isEmail(String str) {
        String regex = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+";
        Pattern pattern = Pattern.compile(regex);
        try {
            String input = str.trim();
            return pattern.matcher(input).matches() ? true : false;
        } catch (Exception exception) {
            return false;
        }
    }

    private boolean isName(String str) {
        String regex = "[\\p{L}]+";
        Pattern pattern = Pattern.compile(regex);
        try {
            String input = str.trim();
            return pattern.matcher(input).matches() ? true : false;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean isCorrectUserDTO(UserDTO userDTO) {
        return isCorrectNumber(userDTO.id()) && isName(userDTO.name()) && isEmail(userDTO.email()) && isCorrectNumber(userDTO.age());
    }
}
