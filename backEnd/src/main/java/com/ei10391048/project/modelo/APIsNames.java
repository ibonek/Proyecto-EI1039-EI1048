package com.ei10391048.project.modelo;

public enum APIsNames {
    WEATHER("OpenWeather"),
    NEWS("Currents"),
    EVENTS("TicketMaster");

    private String nombreAPI;

    private APIsNames(String nombreAPI){
        this.nombreAPI=nombreAPI;
    }
    public String getApiName(){
        return nombreAPI;
    }

}
