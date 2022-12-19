package com.ei10391048.project.controlador;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.modelo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {


    public Boolean confirmation=null;





    @PostMapping("/addLocation")
    public void createLocation(@RequestBody String location) {
        try {
        location = location.trim();
        LocationManager locationManager = LocationManager.getInstance();
        locationManager.setLocationApi(new GeoCodService());
        if (InputValidator.isCoordinates(location)) {
            double[] coordinateInput = InputValidator.formatingInputCoords(location);
            Coordinates coordinates = new Coordinates(coordinateInput[0], coordinateInput[1]);
            locationManager.getLocationApi().setSearch(new ByCoordinates(coordinates));
        } else {
            location = InputValidator.formatingInputName(location);

            locationManager.getLocationApi().setSearch(new ByName(location));
        }

            locationManager.addLocation();
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
    public List<Location> getActiveLocationList() {
        LocationManager manager = LocationManager.getInstance();
        return manager.getLocations();
    }
}
