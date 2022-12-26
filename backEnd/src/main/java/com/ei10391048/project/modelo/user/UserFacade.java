package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.IncorrectUserException;

import java.util.List;

public interface UserFacade {
    User getUser(String email) throws IncorrectUserException;

    void registerUser(String email, String password) throws IncorrectUserException;

    int getNumberOfUsers();
}
