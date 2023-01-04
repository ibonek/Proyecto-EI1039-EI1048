package com.ei10391048.project.modelo.information;

import com.ei10391048.project.modelo.information.APIInformation;

public class WeatherInformation extends APIInformation {
    @Override
    public String toString() {
        return "WeatherInformation{" +
                "temperature=" + temperature +
                ", weatherState='" + weatherState + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", humidity=" + humidity +
                ", wind=" + wind +
                ", apiName='" + apiName + '\'' +
                ", locationName='" + locationName + '\'' +
                ", date=" + date +
                '}';
    }

    double temperature;

    String weatherState;

    int humidity;
    double wind;

    public void setTemperature(double temperature){
        this.temperature=temperature;
    }

    public void setWeatherState(String weatherState){
        this.weatherState=weatherState;
    }


    public void setHumidity(int value){
        humidity=value;

    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getWeatherState() {
        return weatherState;
    }


    public int getHumidity() {
        return humidity;
    }
    public void setDate(String date) {
        String[] aux = date.split("T");
        this.date = aux[0]+": "+aux[1];
        if (this.date.contains("Z")){
            this.date = this.date.substring(0,this.date.length()-1);
        }
    }
}
