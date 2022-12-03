package com.ei10391048.project.controlador;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.modelo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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

        location = location.trim();
        LocationManager locationManager = LocationManager.getInstance();
        locationManager.setLocationApi(new GeoCodService());
        if (isCoordinates(location)) {
            double[] coordinateInput = formatingInputCoords(location);
            Coordinates coordinates = new Coordinates(coordinateInput[0], coordinateInput[1]);
            locationManager.getLocationApi().setSearch(new ByCoordinates(coordinates));
        } else {
            location = formatingInputName(location);

            locationManager.getLocationApi().setSearch(new ByName(location));
        }
        try {
            locationManager.addLocation();
            confirmation = true;
        } catch (IncorrectLocationException ex) {
            confirmation = false;
        }
    }

    public static double[] formatingInputCoords(String input){
        double[] format= new double[2];
        String[] array;
        if (input.contains("º")){
            if (input.contains(",")){
                array = input.split(",");
            } else {
                array = input.split(" ");
            }
            for (int i = 0;i < 2 ;i++) {
                format[i]=transformCoords(array[i]);
            }

        } else {
            if (input.contains(",")) {
                array = input.split(",");
            } else {
                array = input.split(" ");
            }
            for (int i=0;i<2;i++) {
                format[i] = Double.parseDouble(array[i]);
            }

        }
        return format;
    }


    public static double transformCoords(String coord){
        int multiplicador = 1;
        if (coord.toUpperCase().contains("W") ||  coord.toUpperCase().contains("S") ||  coord.toUpperCase().contains("O")) {
            multiplicador=-1;
        }
        coord=coord.substring(0,coord.length()-1);
        double grados = Double.parseDouble(coord.substring(0,coord.indexOf("º")));
        double minutos = Double.parseDouble(coord.substring(coord.indexOf("º")+1,coord.indexOf("'")))/60;
        double segundos = Double.parseDouble(coord.substring(coord.indexOf("'")+1,coord.length()-1))/3600;
        return multiplicador * (grados+minutos+segundos);
        }



    public static boolean isCoordinates(String input){

        String[] array = input.split(",");
        if (array.length == 2 ){
            return checkLatLonCorrect(array);
        }
        array = input.split(" ");
        if (array.length == 2 ){
            return checkLatLonCorrect(array);
        }
        return false;
    }

    private static boolean checkLatLonCorrect(String[] array){
        if (array[0].contains("º") && array[0].contains("'") && array[0].contains("\"") && array[1].contains("º") && array[1].contains("'") && array[1].contains("\"")){
            return true;
        }
        if ((array[0].toUpperCase().contains("N") || array[0].toUpperCase().contains("S")) && (array[1].toUpperCase().contains("O")) ||array[1].toUpperCase().contains("W") ||array[1].toUpperCase().contains("E")){
            return true;
        }
        try{
            Double.parseDouble(array[0]);
            Double.parseDouble(array[1]);

            return true;
        } catch (Exception ex){
            return false;
        }
    }

    @GetMapping("/addLocation")
    public Boolean giveConfirmation(){
        while (confirmation==null);

        return confirmation;

    }

    public static String formatingInputName(String input){
        //Quitamos tildes
        input=StringUtils.stripAccents(input);
        //Ponemos inicial en mayúscula
        input= input.substring(0,1).toUpperCase()+ input.substring(1).toLowerCase();
        return input;
    }


    public static void main(String[] args) {
        String a="2º 1".strip();
        String[] c = a.split(",");
        //a.substring(a.)

        //double b = Double.parseDouble(c[0]);
        System.out.println(Arrays.toString(Arrays.stream(c).toArray()));
    }

}
