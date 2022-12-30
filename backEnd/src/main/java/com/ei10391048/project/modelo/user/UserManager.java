package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.AlreadyExistentUser;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.fireBase.CRUDFireBase;

import java.util.List;

public class UserManager implements UserManagerFacade {

    private static UserManager userManager = null;

    private final List<User> userList;

    private CRUDFireBase crudFireBase;

    private UserManager(){
        crudFireBase=CRUDFireBase.getInstance();
        userList = crudFireBase.getUsers();
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
    public User registerUser(String email, String password) throws IncorrectUserException{

        if (password == null ||email == null ||email.length() == 0 ||password.length() == 0){
            throw new IncorrectUserException();
        }
        for (User user: userList){
            if (email.equalsIgnoreCase(user.getEmail())){
                throw new IncorrectUserException();
            }
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        this.userList.add(user);
        try {
            crudFireBase.signUp(email, password);
        } catch (AlreadyExistentUser e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public int getNumberOfUsers() {
        return userList.size();
    }

    @Override
    public void deleteAllUsers() {
        userList.clear();
        crudFireBase.deleteAllUsers();
    }
}
