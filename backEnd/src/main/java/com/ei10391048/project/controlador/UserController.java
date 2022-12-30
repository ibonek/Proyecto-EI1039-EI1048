package com.ei10391048.project.controlador;

import com.ei10391048.project.exception.AlreadyExistentUser;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.modelo.Coordinates;
import com.ei10391048.project.modelo.user.UserFacade;
import com.ei10391048.project.modelo.user.UserManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    public Boolean confirmation=null;


    @PostMapping("/registerUser")
    public void registerUser(@RequestBody String body)  {
        String[] aux = body.split("#");
        String password = aux[1];
        String email = aux[0].toLowerCase();

        UserManager manager = UserManager.getInstance();
        confirmation=true;
        try {
            manager.registerUser(email, password);
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


}
