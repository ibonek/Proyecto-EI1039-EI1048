package com.ei10391048.project.modelo;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;

public class OpenWeather extends API{

    private static OpenWeather instance;
    private OpenWeatherMapClient openWeatherMapClient;

    private Weather weather;
    private OpenWeather(){
        apiKey = "4d8fb5b93d4af21d66a2948710284366";
        openWeatherMapClient = new OpenWeatherMapClient(apiKey);

    }
    public static OpenWeather getInstance(){
        if (instance == null){
            instance = new OpenWeather();
        }
        return instance;

    }



    @Override
    String insertAPIName() {
        return APIsNames.WEATHER.getApiName();
    }

    @Override
    String insertLocationName() {

        return weather.getLocation().getName();
    }

    @Override
    void apiCall(String locationName) {
        weather = openWeatherMapClient.currentWeather().single().byCityName(locationName).language(Language.SPANISH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
    }
}
