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

    @GetMapping("/apiInfo")
    public  List<APIInformation> getAPIsInfo() throws IncorrectLocationException {
        List<APIInformation> list = new LinkedList<>();
        LocationManager locationManager = LocationManager.getInstance();
        /*
        GeoCodService geoCodSrv = new GeoCodService();
        String toponimo = "Valencia";
        geoCodSrv.setSearch(new ByName(toponimo));
        locationManager.setLocationApi(geoCodSrv);
        locationManager.addLocation();

        toponimo = "Madrid";
        geoCodSrv.setSearch(new ByName(toponimo));

        locationManager.addLocation();
*/


            for (Location location: locationManager.getLocations()){
                APIManager manager = location.getApiManager();
                manager.addAPI(new OpenWeather());
                list.add(manager.getInfo(location.getName()));
            }
            return list;

        }



    public static void main(String[] args) throws IncorrectLocationException {
        //InformationController informationController = new InformationController();
        //System.out.println(informationController.getAPIsInfo());
    }

}
