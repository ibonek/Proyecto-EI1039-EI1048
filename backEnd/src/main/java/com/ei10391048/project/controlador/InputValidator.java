package com.ei10391048.project.controlador;

import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.LocationManager;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class InputValidator {
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
        coord = coord.trim();
        coord = coord.toUpperCase();
        int multiplicador = 1;
        if (coord.toUpperCase().contains("W") ||  coord.toUpperCase().contains("S") ||  coord.toUpperCase().contains("O")) {
            multiplicador=-1;
        }
        if (coord.charAt(0) == 'W' || coord.charAt(0) == 'E' || coord.charAt(0) == 'O' || coord.charAt(0) == 'N' || coord.charAt(0) == 'S')
            coord = coord.substring(1);
        else{
            coord=coord.substring(0,coord.length()-1);
        }
        double grados = Double.parseDouble(coord.substring(0,coord.indexOf("º")));
        double minutos = Double.parseDouble(coord.substring(coord.indexOf("º")+1,coord.indexOf("'")))/60;
        double segundos = Double.parseDouble(coord.substring(coord.indexOf("'")+1,coord.length()-1))/3600;
        return multiplicador * (grados+minutos+segundos);
    }



    public static boolean isCoordinates(String input) {

        String[] array = input.split(",");
        if (array.length == 2) {
            return checkLatLonCorrect(array);
        }
        array = input.split(" ");
        if (array.length == 2) {

            try {
                Double.parseDouble(String.valueOf(array[0].charAt(1)));
                Double.parseDouble(String.valueOf(array[1].charAt(1)));
                return checkLatLonCorrect(array);
            } catch (Exception ex) {
                return false;
            }

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

    public static String formatingInputName(String input){
        String aux = "";
        //Quitamos tildes
        input= StringUtils.stripAccents(input);
        //Ponemos inicial en primera letra de cada palabra
        for (String palabra: input.split(" ")){
            aux += palabra.substring(0,1).toUpperCase()+ palabra.substring(1).toLowerCase();
            aux += " ";
        }

        return aux.trim();
    }

    public static List<String> generateAutocompleteList() throws IOException, InterruptedException {
        ArrayList<String> list = new ArrayList<>();
        int numberOfLocations = 1000;
        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create("https://data.opendatasoft.com/api/records/1.0/search/?dataset=geonames-all-cities-with-a-population-1000%40public&q=&lang=ES&rows="+numberOfLocations+"&sort=population&refine.country_code=ES"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONArray json = new JSONArray(response.body().substring(response.body().indexOf("records")+10));


        for (int i=0; i<numberOfLocations; i++){
            JSONObject jsonObject = new JSONObject(json.getJSONObject(i).get("fields").toString());
            list.add(jsonObject.getString("name"));
        }
        return list;
    }
    public static String urlLocationName(String location){
        String[] aux = location.split(" ");

        String urlName = aux[0];
        for (int i=1;i< aux.length;i++){
            if (aux[i].equalsIgnoreCase("de") || aux[i].equalsIgnoreCase("la") || aux[i].equalsIgnoreCase("el")){
                aux[i] = aux[i].toLowerCase();
            }
            urlName += "_"+aux[i];
        }
        return urlName;
    }

}
