package com.ei10391048.project.controlador;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NonExistingAPIException;
import com.ei10391048.project.modelo.*;
import com.ei10391048.project.modelo.ApiFacade;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.APIsNames;
import com.ei10391048.project.modelo.information.APIInformation;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class InformationController {

    private Boolean confirmation =  null;
    @GetMapping("/apiInfo")
    public  List<List<List<APIInformation>>> getAPIsInfo() {
        LocationManager locationManager = LocationManager.getInstance();

        return locationManager.getAllActivatedInfo();
        }

    @GetMapping("/activeLocationsNames")
    public  List<String> getLocationsNames() {

        List<String> list = new LinkedList<>();
        LocationManager locationManager = LocationManager.getInstance();

        list.add("All");
        for (Location location: locationManager.getActiveLocations()){
            list.add(location.getAlias());
        }
        return list;

    }

    @PostMapping("/changeActiveApiState")
    public void changeActiveApiState(@RequestBody int order) {
        LocationManager manager = LocationManager.getInstance();
        try {
            manager.changeApiState(order);
            confirmation = true;
        } catch (NonExistingAPIException e) {
            confirmation = false;
        }
    }

    @GetMapping("/changeActiveApiState")
    public Boolean giveConfirmation(){
        while (confirmation==null);

        return confirmation;
    }
}
