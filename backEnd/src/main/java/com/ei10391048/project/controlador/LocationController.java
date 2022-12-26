package com.ei10391048.project.controlador;
import com.ei10391048.project.exception.IncorrectAliasException;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.modelo.*;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.user.User;
import com.ei10391048.project.modelo.user.UserFacade;
import com.ei10391048.project.modelo.user.UserManager;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {

    public Boolean confirmation=null;

    @PostMapping("/addLocation")
    public void createLocation(Map<String, String> body) {
        String location = body.get("location");
        try {
        UserFacade user = UserManager.getInstance().getUser(body.get("user"));
        location = location.trim();
        if (InputValidator.isCoordinates(location)) {
            double[] coordinateInput = InputValidator.formatingInputCoords(location);
            Coordinates coordinates = new Coordinates(coordinateInput[0], coordinateInput[1]);
            user.addLocation(coordinates);
        } else {
            location = InputValidator.formatingInputName(location);
            user.addLocation(location);

        }
            confirmation = true;
        } catch (Exception ex) {
            confirmation = false;
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

        LocationManager manager = UserManager.getInstance().getUser(email).getLocationManager();
        return manager.getLocations();
    }

    @PostMapping("/changeActiveState")
    public void changeActiveState(Map<String, String> body) throws IncorrectLocationException, IncorrectUserException {
        LocationManager manager = UserManager.getInstance().getUser(body.get("user")).getLocationManager();
        try {
            Location loc = manager.getLocation(body.get("location"));
            loc.setActive(!loc.getIsActive());
        } catch (IncorrectLocationException e) {
            throw new IncorrectLocationException();
        }
    }

    @PostMapping("/changeAlias")
    public void changeAlias(Map<String, String> body) throws IncorrectLocationException, IncorrectAliasException, IncorrectUserException {
        LocationManager manager = UserManager.getInstance().getUser(body.get("user")).getLocationManager();
        String alias = body.get("location");
        try {
            String[] vec = alias.split("#");
            Location location = manager.getLocation(vec[0]);
            location.setAlias(vec[1]);
        } catch ( IncorrectAliasException e) {
            throw new IncorrectAliasException();
        }
    }

    @PostMapping("/deleteLocation")
    public void deleteLocation(@RequestBody Map<String, String> body) throws IncorrectLocationException, IncorrectUserException {
        LocationManager manager = UserManager.getInstance().getUser(body.get("user")).getLocationManager();

        try {
            manager.deleteLocation(body.get("location"));
        } catch (IncorrectLocationException e) {
            throw new IncorrectLocationException();
        }
    }

    @GetMapping("/giveAvailableApis")
    public List<API> giveAvailableApis(@RequestParam String email) throws IncorrectUserException {
        InformationLocationManager manager = UserManager.getInstance().getUser(email).getInformationLocationManager();

        return manager.getApis();
    }
}
