package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.AlreadyExistentUser;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.fireBase.CRUDFireBase;

import java.util.List;

public class UserManager implements UserManagerFacade {

    private static UserManager userManager = null;

    private final List<User> userList;

    private final CRUDFireBase crudFireBase;

    private UserManager(){
        crudFireBase=CRUDFireBase.getInstance();
        userList = crudFireBase.getUsers();
    }

    public List<User> getUserList() {
        return userList;
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
    public void registerUser(String email) throws IncorrectUserException, AlreadyExistentUser {
        if (email == null ||email.length() == 0){
            throw new IncorrectUserException();
        }
        User user = new User();
        user.setEmail(email);
        this.userList.add(user);
        try {
            crudFireBase.addUser(email);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int getNumberOfUsers() {
        return userList.size();
    }

    @Override
    public void deleteAllUsers() {
        userList.clear();
        try {
            crudFireBase.deleteUsers();
        } catch (IncorrectUserException e) {
            throw new RuntimeException(e);
        }
    }

    public void signOut(String email)throws IncorrectUserException{
        userList.remove(getUser(email));
    }
}
