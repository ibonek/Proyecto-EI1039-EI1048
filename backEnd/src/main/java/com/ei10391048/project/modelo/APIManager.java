package com.ei10391048.project.modelo;

import com.ei10391048.project.modelo.api.*;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.LinkedList;
import java.util.List;


public class APIManager implements ApiFacade{


    private List<API> apiList;

    private final List<List<APIInformation>> apiInformation;

    public APIManager() {
        apiList = new LinkedList<>();
        apiInformation = new LinkedList<>();
        apiList.add(APIsNames.WEATHER.getOrder(),new OpenWeather());
        apiList.add(APIsNames.EVENTS.getOrder(),new TicketMaster());
        apiList.add(APIsNames.NEWS.getOrder(),new NewsAPI());
    }


    public void setApiList(List<API> list){
        this.apiList=list;
    }


    public void generateInfo(String locationName) {

        for (API api: apiList){
            List<APIInformation> info = api.generateInfo(locationName);
            if (info != null)
                apiInformation.add(info);
        }

    }

    @Override
    public List<APIInformation> getWeatherInformation() {
        return apiInformation.get(APIsNames.WEATHER.getOrder());
    }

    @Override
    public  List<APIInformation> getEventsInformation() {
        return apiInformation.get(APIsNames.EVENTS.getOrder());
    }

    @Override
    public  List<APIInformation> getNewsInformation() {

        return apiInformation.get(APIsNames.NEWS.getOrder());

    }
}
