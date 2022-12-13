package com.ei10391048.project.modelo.api;

import com.ei10391048.project.modelo.APIsNames;
import com.ei10391048.project.modelo.information.APIInformation;
import com.ei10391048.project.modelo.information.EventInformation;
import com.ei10391048.project.modelo.information.WeatherInformation;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;

import java.util.LinkedList;
import java.util.List;

public class OpenWeather extends API {

    private final OpenWeatherMapClient openWeatherMapClient;

    private final List<APIInformation> information;

    private Weather weather;
    public OpenWeather(){
        apiKey = "96a01f139118d49f85a54068a7321e3d";
        openWeatherMapClient = new OpenWeatherMapClient(apiKey);
        information  = super.information;
        information.add(new WeatherInformation());
        name = APIsNames.WEATHER.getApiName();


    }



    @Override
    void insertAPIName() {
        information.get(0).setApiName(name);
    }

    @Override
    void insertLocationName() {
        information.get(0).setLocationName(weather.getLocation().getName());
    }

    @Override
    void insertImageURL() {
        information.get(0).setImageURL(weather.getWeatherState().getWeatherIconUrl());

    }

    @Override
    void insertDate() {
        information.get(0).setDate(weather.getCalculationTime().toString());
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
    void insertBodyData() {
        WeatherInformation info = (WeatherInformation) information.get(0);
        info.setTemperature(weather.getTemperature().getValue());
        info.setWeatherState(weather.getWeatherState().getName());
        info.setHumidity(weather.getHumidity().getValue());
        info.setWind(weather.getWind().getSpeed());

    }



}
