package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.AlreadyExistentUser;
import com.ei10391048.project.exception.IncorrectUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserManagerTest {
    UserManager manager = UserManager.getInstance();
    @Test
    public void registerValidUserTest() throws IncorrectUserException, AlreadyExistentUser {
        int num = manager.getNumberOfUsers();
        manager.registerUser("test@gmail.com");
        assertEquals(num+1, manager.getNumberOfUsers());
    }

    @ParameterizedTest
    @MethodSource("invalidUser")
    public void registerInvalidUserTest(String email){
        int num = manager.getNumberOfUsers();
        try {
            manager.registerUser(email);
        }catch (IncorrectUserException | AlreadyExistentUser ex){
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
