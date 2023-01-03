package com.ei10391048.project.modelo;

import com.ei10391048.project.modelo.api.*;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class APIManager implements ApiFacade{


    private List<API> apiList;

    private List<List<APIInformation>> apiInformation;

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


    @Override
    public List<APIInformation> getInformation(int order) {
        APIsNames api = APIsNames.values()[order];
        return switch (api) {
            case WEATHER -> this.getWeatherInformation();
            case EVENTS -> this.getEventsInformation();
            case NEWS -> this.getNewsInformation();
        };
    }

    public void generateInfo(String locationName) {
        apiInformation = new LinkedList<>();
        for (API api: apiList){
            List<APIInformation> info = api.generateInfo(locationName);
            apiInformation.add(Objects.requireNonNullElseGet(info, LinkedList::new));
        }
    }

    public List<APIInformation> getWeatherInformation() {
        return apiInformation.get(APIsNames.WEATHER.getOrder());
    }

    public  List<APIInformation> getEventsInformation() {
        return apiInformation.get(APIsNames.EVENTS.getOrder());
    }

    public  List<APIInformation> getNewsInformation() { return apiInformation.get(APIsNames.NEWS.getOrder()); }

    public List<API> getApiList(){
        return apiList;
    }

    @Override
    public void copyApiListState(List<API> list) {
        for (int i=0; i<apiList.size();i++){
            apiList.get(i).setActive(list.get(i).getIsActive());
        }
    }

}
