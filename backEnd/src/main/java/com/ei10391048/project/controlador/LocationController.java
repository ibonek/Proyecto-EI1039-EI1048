package com.ei10391048.project.controlador;
import com.ei10391048.project.exception.*;
import com.ei10391048.project.modelo.*;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.user.User;
import com.ei10391048.project.modelo.user.UserFacade;
import com.ei10391048.project.modelo.user.UserManager;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {

    public Boolean confirmation=null;

    @PostMapping("/addLocation")
    public void createLocation(@RequestBody String body) {
        String[] aux = body.split("#");
        String location = aux[1];
        String email = aux[0];
        try {
        location = location.trim();
        UserManager manager = UserManager.getInstance();
        User user=manager.getUser(email);
        if (InputValidator.isCoordinates(location)) {
            double[] coordinateInput = InputValidator.formatingInputCoords(location);
            Coordinates coordinates = new Coordinates(coordinateInput[0], coordinateInput[1]);
            user.addUserLocation(coordinates);
        } else {
            location = InputValidator.formatingInputName(location);
            user.addUserLocation(location);

        }
            confirmation = true;
        } catch (Exception ex) {
            confirmation = false;
            ex.printStackTrace();
        }
    }


    @GetMapping("/addLocation")
    public Boolean giveConfirmation(){
        while (confirmation==null);

        return confirmation;
    }

    @GetMapping("/giveAutocompleteLocations")
    public List<String> giveCityList() throws IOException, InterruptedException {
        return InputValidator.generateAutocompleteList();
    }

    @GetMapping("/giveLocations")
    public List<Location> getLocationList(@RequestParam String email) throws IncorrectUserException {
        UserFacade user = UserManager.getInstance().getUser(email);
        return user.getUserLocations();
    }

    @PostMapping("/changeActiveState")
    public void changeActiveState(@RequestBody String body) throws IncorrectLocationException, IncorrectUserException {
        String[] aux = body.split("#");
        String location = aux[1];
        String email = aux[0];
        UserFacade user = UserManager.getInstance().getUser(email);
        try {
            Location loc = user.getLocationManager().getLocation(location);
            loc.setActive(!loc.getIsActive());
        } catch (IncorrectLocationException e) {
            throw new IncorrectLocationException();
        }
    }

    @PostMapping("/changeAlias")
    public void changeAlias(@RequestBody String body) throws IncorrectLocationException, IncorrectAliasException, IncorrectUserException {
        String[] aux = body.split("#");

        String name = aux[1];
        String alias = aux[2];
        String email = aux[0];
        UserFacade user = UserManager.getInstance().getUser(email);
        try {
            Location location = user.getLocation(name);
            location.setAlias(alias);
        } catch ( IncorrectAliasException e) {
            throw new IncorrectAliasException();
        }
    }

    @PostMapping("/deleteLocation")
    public void deleteLocation(@RequestBody String body) throws IncorrectLocationException, IncorrectUserException {
        String[] aux = body.split("#");

        String location = aux[1];
        String email = aux[0];
        UserFacade user =  UserManager.getInstance().getUser(email);

        try {
            user.deleteLocation(location);
        } catch (IncorrectLocationException e) {
            throw new IncorrectLocationException();
        }
    }

    @GetMapping("/giveAvailableApis")
    public List<API> giveAvailableApis(@RequestParam String email) throws IncorrectUserException {
        UserFacade user = UserManager.getInstance().getUser(email);

        return user.getApis();
    }
/*
    @PostMapping("/addLocation")
    public void addUserLocation(@RequestBody String body) throws IncorrectUserException, IncorrectLocationException, NotSavedException {
        String[] aux = body.split("#");
        String location = aux[1];
        String email = aux[0];
        UserFacade user = UserManager.getInstance().getUser(email);
        String[] aux2 = location.split(",");
        if (aux2.length == 2) {
            double latitude = Double.parseDouble(aux2[0]);
            double longitude = Double.parseDouble(aux2[1]);
            Coordinates coordinates = new Coordinates(latitude, longitude);
            try {
                user.addUserLocation(coordinates);
            } catch (AlreadyExistentLocationException e) {
                confirmation = false;
            }
        } else {
            try {
                user.addUserLocation(location);
            } catch (AlreadyExistentLocationException e) {
                confirmation = false;
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }*/
}
