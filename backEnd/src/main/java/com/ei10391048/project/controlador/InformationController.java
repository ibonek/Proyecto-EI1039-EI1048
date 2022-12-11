package com.ei10391048.project.controlador;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.modelo.*;
import com.ei10391048.project.modelo.api.OpenWeather;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class InformationController {

    @GetMapping("/weatherState")
    public List<WeatherInformation> getWeatherInfo() {
        LinkedList<WeatherInformation> list = new LinkedList<>();
        LocationManager locationManager = LocationManager.getInstance();

        for (Location location: locationManager.getLocations()){
            APIManager manager = location.getApiManager();
            manager.addAPI(new OpenWeather());
            list.add((WeatherInformation) manager.getInfo(location.getName()));
        }
        return list;

    }



}
