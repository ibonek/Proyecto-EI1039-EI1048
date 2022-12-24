package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import com.github.prominence.openweathermap.api.model.weather.Weather;

public class ByName implements  SearchInterface{

    private String name;
    OpenWeatherMapClient openWeatherMapClient;

    public ByName(String name) {

        this.name = name;
        openWeatherMapClient = new OpenWeatherMapClient(apiKey);
        openWeatherMapClient.setReadTimeout(2000);

    }

    public Location search() throws IncorrectLocationException {
        Location location = new Location();
        try {
            Weather weather = openWeatherMapClient.currentWeather().single().byCityName(name).language(Language.SPANISH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
            location.setName(weather.getLocation().getName());
            location.setCoordinates(new Coordinates(weather.getLocation().getCoordinate().getLatitude(),weather.getLocation().getCoordinate().getLongitude()));
            return location;
        } catch (NoDataFoundException ex){
            throw new IncorrectLocationException();
        }

    }


}
