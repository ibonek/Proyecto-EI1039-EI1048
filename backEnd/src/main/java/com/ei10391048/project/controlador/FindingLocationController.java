package com.ei10391048.project.controlador;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.modelo.ByName;
import com.ei10391048.project.modelo.GeoCodService;
import com.ei10391048.project.modelo.LocationManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FindingLocationController {

    private final FindingLocationRepository findingLocationRepository;

    public Boolean confirmation=null;


    public FindingLocationController(FindingLocationRepository findingLocationRepository) {
        this.findingLocationRepository = findingLocationRepository;
    }


    @PostMapping("/addLocation")
    public void createLocation(@RequestBody String location) {

        String locationName = formatingInput(location);
        //Comprobamos que sea coordenada o name

        //Llamamos a quien sea necesario
        LocationManager locationManager = LocationManager.getInstance();

        locationManager.setLocationApi(new GeoCodService());
        locationManager.getLocationApi().setSearch(new ByName(locationName));

        try {
            locationManager.addByName();
            confirmation=true;
        } catch (IncorrectLocationException ex){
            confirmation=false;
        }



    }


    @GetMapping("/addLocation")
    public Boolean giveConfirmation(){
        while (confirmation==null);

        return confirmation;

    }

    public static String formatingInput(String input){
        //Quitamos espacios
        input=input.trim();


        //Quitamos tildes
        input=StringUtils.stripAccents(input);
        //Ponemos inicial en mayúscula
        input= input.substring(0,1).toUpperCase()+ input.substring(1).toLowerCase();
        return input;
    }

    public static void main(String[] args) {
        String a=" 2 1";
        double b = Double.parseDouble(a);
        System.out.println(b);
    }

}
