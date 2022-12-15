package com.ei10391048.project.controlador;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.modelo.*;
import com.ei10391048.project.modelo.api.NewsAPI;
import com.ei10391048.project.modelo.api.OpenWeather;
import com.ei10391048.project.modelo.api.TicketMaster;
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
    public  List<List<List<APIInformation>>> getAPIsInfo() throws IncorrectLocationException {

        List<List<List<APIInformation>>> list = new LinkedList<>();
        LocationManager locationManager = LocationManager.getInstance();


        /*

        GeoCodService geoCodSrv = new GeoCodService();
        String toponimo = "London";
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
                manager.addAPI(new TicketMaster());
                manager.addAPI(new NewsAPI());

                list.add(manager.getInfo(location.getName()));
            }
        //System.out.println(list.get(0).get(2).get(1));
            return list;

        }



    public static void main(String[] args) throws IncorrectLocationException {

        InformationController informationController = new InformationController();
        List<List<List<APIInformation>>> info = informationController.getAPIsInfo();
        System.out.println(info.get(0).get(2).get(0));
        System.out.println(info.get(0).get(1).get(0));
        System.out.println(info.get(0).get(2).get(1));
        System.out.println(info.get(0).get(0).get(0));
        System.out.println(info.get(0).get(0).get(1));



    }

}
