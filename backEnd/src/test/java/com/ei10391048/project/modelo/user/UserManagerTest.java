package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.IncorrectUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserManagerTest {
    UserManager manager = UserManager.getInstance();
    @Test
    public void registerValidUserTest() throws IncorrectUserException {
        int num = manager.getNumberOfUsers();
        manager.registerUser("test@gmail.com","1234a");
        assertEquals(num+1, manager.getNumberOfUsers());
    }

    @ParameterizedTest
    @MethodSource("invalidUser")
    public void registerInvalidUserTest(String email, String password){
        int num = manager.getNumberOfUsers();
        try {
            manager.registerUser(email,password);
        }catch (IncorrectUserException ex){
            assertEquals(num,manager.getNumberOfUsers());
        }
    }

    static Stream<Arguments> invalidUser(){
        return Stream.of(
                Arguments.of("",""),
                Arguments.of(null,"1234a"),
                Arguments.of("test@gmail.com",null),
                Arguments.of("test@gmail.com","12"),
                Arguments.of("test@gmail.com","ab")
                );
    }
}