package com.ei10391048.project.controlador;

import com.ei10391048.project.modelo.*;
import com.ei10391048.project.modelo.ApiFacade;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.APIsNames;
import com.ei10391048.project.modelo.information.APIInformation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class InformationController {

    @GetMapping("/apiInfo")
    public  List<List<List<APIInformation>>> getAPIsInfo() {
        LocationManager locationManager = LocationManager.getInstance();

        return locationManager.getAllActivatedInfo();
        }

    @GetMapping("/locationsNames")
    public  List<String> getLocationsNames() {

        List<String> list = new LinkedList<>();
        LocationManager locationManager = LocationManager.getInstance();

        list.add("All");
        for (Location location: locationManager.getLocations()){
            list.add(location.getName());
        }
        return list;

    }


}
