package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.AlreadyExistentUser;
import com.ei10391048.project.exception.IncorrectUserException;

public interface UserManagerFacade {
    User getUser(String email) throws IncorrectUserException;

    void registerUser(String email) throws IncorrectUserException, AlreadyExistentUser;

    int getNumberOfUsers();

    void deleteAllUsers();
}
