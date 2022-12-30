package com.ei10391048.project.controlador;

import com.ei10391048.project.exception.AlreadyExistentUser;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.modelo.user.UserManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    public Boolean confirmation=null;


    @PostMapping("/registerUser")
    public void registerUser(@RequestBody String body)  {
        String email = body.toLowerCase();

        UserManager manager = UserManager.getInstance();
        confirmation=true;
        try {
            manager.registerUser(email);
        } catch (IncorrectUserException | AlreadyExistentUser ex){
            ex.printStackTrace();
            confirmation=false;
        }
    }

    @GetMapping("/registerUser")
    public Boolean giveConfirmation(){
        while  (confirmation ==null);
        return confirmation;
    }


    @PostMapping("/signOut")
    public void signOut(@RequestBody String email) throws IncorrectUserException {
        UserManager manager = UserManager.getInstance();
        manager.signOut(email);
    }
}
