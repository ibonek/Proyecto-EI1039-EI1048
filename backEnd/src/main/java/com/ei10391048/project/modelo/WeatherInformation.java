package com.ei10391048.project.modelo;

public class WeatherInformation extends APIInformation{

    double temperature;

    String weatherState;

    String imageURL;

    int humidity;

    public void setTemperature(double temperature){
        this.temperature=temperature;
    }

    public void setWeatherState(String weatherState){
        this.weatherState=weatherState;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setHumidity(int value){
        humidity=value;

    }

    public double getTemperature() {
        return temperature;
    }

    public String getWeatherState() {
        return weatherState;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getHumidity() {
        return humidity;
    }
}
