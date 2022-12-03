package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import com.github.prominence.openweathermap.api.model.Coordinate;
import com.github.prominence.openweathermap.api.model.weather.Weather;

public class ByCoordinates implements SearchInterface{
    private Coordinates coordinates;
    OpenWeatherMapClient openWeatherMapClient;

    public ByCoordinates(Coordinates coordinates) {
        openWeatherMapClient = new OpenWeatherMapClient(apiKey);
        openWeatherMapClient.setReadTimeout(1000);
        openWeatherMapClient.setReadTimeout(1000);
        this.coordinates = coordinates;
    }
    @Override
    public Location search() throws IncorrectLocationException {
        Location location = new Location();
        try {
            Weather weather = openWeatherMapClient.currentWeather().single().byCoordinate(Coordinate.of(coordinates.getLat(), coordinates.getLon() )).language(Language.SPANISH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
            location.setName(weather.getLocation().getName());
            Coordinates newCoords = new Coordinates(weather.getLocation().getCoordinate().getLatitude(),weather.getLocation().getCoordinate().getLongitude());
            location.setCoordinates(newCoords);
            return location;
        } catch (NoDataFoundException| IllegalArgumentException ex){
            throw new IncorrectLocationException();
        }
    }


}
