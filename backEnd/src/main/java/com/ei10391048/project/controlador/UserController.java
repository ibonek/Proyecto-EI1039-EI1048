package com.ei10391048.project.controlador;

import com.ei10391048.project.exception.AlreadyExistentUser;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.modelo.user.User;
import com.ei10391048.project.modelo.user.UserManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @PostMapping("/registerUser")
    public void registerUser(@RequestBody String email)  {
        UserManager manager = UserManager.getInstance();
        try {
            manager.registerUser(email);
        } catch (IncorrectUserException | AlreadyExistentUser ex){
            throw new RuntimeException(ex);
        }
    }

    @PostMapping("signIn")
    public void signIn(@RequestBody String email)  {
        UserManager manager = UserManager.getInstance();
        try {
            User user = manager.getUser(email);
            user.signIn();
        } catch (IncorrectUserException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signOut")
    public void signOut(@RequestBody String email) throws IncorrectUserException {
        UserManager manager = UserManager.getInstance();
        manager.signOut(email);
    }
}
