package com.ei10391048.project.controlador;

import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.modelo.*;
import com.ei10391048.project.modelo.information.APIInformation;
import com.ei10391048.project.modelo.user.User;
import com.ei10391048.project.modelo.user.UserFacade;
import com.ei10391048.project.modelo.user.UserManager;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class InformationController {

    private Boolean confirmation =  null;
    User user;
    @GetMapping("/apiInfo")
    public  List<List<List<APIInformation>>> getAPIsInfo(@RequestParam String email) throws IncorrectUserException {
        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUser(email);

        return user.getAllActivatedInfo();
        }

    @GetMapping("/activeLocationsNames")
    public  List<String> getLocationsNames(@RequestParam String email) throws IncorrectUserException {
        List<String> list = new LinkedList<>();
        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUser(email);
        list.add("All");
        for (Location location: user.getMyLocations()){
            list.add(location.getAlias());
        }
        return list;

    }

    @PostMapping("/changeActiveApiState")
    public void changeActiveApiState(@RequestBody String body) throws IncorrectUserException {
        String[] aux = body.split("#");
        int order = Integer.parseInt(aux[1]);
        String email = aux[0];
        UserFacade user = UserManager.getInstance().getUser(email);
        try {
            user.changeAPIState(order);
            confirmation = true;
        } catch (NotExistingAPIException e) {
            confirmation = false;
        }
    }

    @GetMapping("/changeActiveApiState")
    public Boolean giveConfirmation(){
        while (confirmation==null);

        return confirmation;
    }
}
