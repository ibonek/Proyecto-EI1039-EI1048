package com.ei10391048.project.controlador;
import com.ei10391048.project.exception.IncorrectAliasException;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.modelo.*;
import com.ei10391048.project.modelo.api.API;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {


    public Boolean confirmation=null;

    @PostMapping("/addLocation")
    public void createLocation(@RequestBody String location) {
        try {
        location = location.trim();
        LocationManagerFacade locationManager = LocationManager.getInstance();
        if (InputValidator.isCoordinates(location)) {
            double[] coordinateInput = InputValidator.formatingInputCoords(location);
            Coordinates coordinates = new Coordinates(coordinateInput[0], coordinateInput[1]);
            locationManager.addLocation(coordinates);
        } else {
            location = InputValidator.formatingInputName(location);
            locationManager.addLocation(location);

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
    public List<Location> getLocationList() {
        LocationManagerFacade manager = LocationManager.getInstance();
        return manager.getLocations();
    }

    @PostMapping("/changeActiveState")
    public void changeActiveState(@RequestBody String location) throws IncorrectLocationException {
        LocationManagerFacade manager = LocationManager.getInstance();
        try {
            Location loc = manager.getLocation(location);
            loc.setActive(!loc.getIsActive());
        } catch (IncorrectLocationException e) {
            throw new IncorrectLocationException();
        }
    }

    @PostMapping("/changeAlias")
    public void changeAlias(@RequestBody String alias) throws IncorrectLocationException, IncorrectAliasException {
        LocationManagerFacade manager = LocationManager.getInstance();
        try {
            String[] vec = alias.split("#");
            Location location = manager.getLocation(vec[0]);
            location.setAlias(vec[1]);
        } catch ( IncorrectAliasException e) {
            throw new IncorrectAliasException();
        }
    }

    @PostMapping("/deleteLocation")
    public void deleteLocation(@RequestBody String location) throws IncorrectLocationException {
        LocationManagerFacade manager = LocationManager.getInstance();
        try {
            manager.deleteLocation(location);
        } catch (IncorrectLocationException e) {
            throw new IncorrectLocationException();
        }
    }

    @GetMapping("/giveAvailableApis")
    public List<API> giveAvailableApis() {
        InformationLocationManagerFacade manager = InformationLocationManager.getInstance();
        return manager.getApis();
    }
}
