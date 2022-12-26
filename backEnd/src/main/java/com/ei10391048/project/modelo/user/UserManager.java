package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.IncorrectUserException;

import java.util.LinkedList;
import java.util.List;

public class UserManager implements UserFacade{

    private static UserManager userManager = null;

    private List<User> userList;

    private UserManager(){
        userList = new LinkedList<>();
    }

    public static UserManager getInstance(){
        if (userManager == null){
            userManager = new UserManager();
        }
        return userManager;
    }

    @Override
    public User getUser(String email) throws IncorrectUserException {
        for (User user: userList){
            if (user.getEmail().equals(email)){
                return user;
            }
        }
        throw new IncorrectUserException();
    }

    @Override
    public void registerUser(String email, String password) throws IncorrectUserException{

    }

    @Override
    public int getNumberOfUsers() {
        return userList.size();
    }
}
