package com.ei10391048.project.modelo.api;

import com.ei10391048.project.modelo.APIInformation;
import com.ei10391048.project.modelo.APIsNames;
import com.ei10391048.project.modelo.WeatherInformation;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;

public class OpenWeather extends API {

    private static OpenWeather instance;
    private final OpenWeatherMapClient openWeatherMapClient;

    private final WeatherInformation information;

    private Weather weather;
    public OpenWeather(){
        apiKey = "96a01f139118d49f85a54068a7321e3d";
        openWeatherMapClient = new OpenWeatherMapClient(apiKey);
        information  = new WeatherInformation();
        name = APIsNames.WEATHER.getApiName();


    }



    @Override
    void insertAPIName() {
        information.setApiName(name);
    }

    @Override
    void insertLocationName() {
        information.setLocationName(weather.getLocation().getName());
    }

    @Override
    void insertDate() {
        information.setDate(weather.getCalculationTime());
    }

    @Override
    boolean apiCall(String locationName) {

        try {
            weather = openWeatherMapClient.currentWeather().single().byCityName(locationName).language(Language.ENGLISH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    @Override
    WeatherInformation getInfo() {
        return information;
    }

    @Override
    void insertBodyData() {
        information.setTemperature(weather.getTemperature().getValue());
        information.setWeatherState(weather.getWeatherState().getName());
        information.setImageURL(weather.getWeatherState().getWeatherIconUrl());
        information.setHumidity(weather.getHumidity().getValue());
        information.setWind(weather.getWind().getSpeed());

    }



}
