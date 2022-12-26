package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.IncorrectUserException;

public interface UserManagerFacade {
    User getUser(String email) throws IncorrectUserException;

    User registerUser(String email, String password) throws IncorrectUserException;


    int getNumberOfUsers();

    void deleteAllUsers();
}
