package com.ei10391048.project.modelo.api;

public enum APIsNames {
    WEATHER("OpenWeather",0),
    EVENTS("TicketMaster",1),
    NEWS("NewsAPI",2);


    private String apiName;
    private int order;

    private APIsNames(String apiName, int order){
        this.apiName=apiName;
        this.order=order;
    }
    public String getApiName(){
        return apiName;
    }

    public int getOrder() {
        return order;
    }
}
