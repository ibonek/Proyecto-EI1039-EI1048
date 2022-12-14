package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.modelo.api.*;
import com.ei10391048.project.modelo.information.APIInformation;
import com.ei10391048.project.modelo.user.User;

import java.util.LinkedList;
import java.util.List;

public class InformationLocationManager {
    private List<API> apiList;
    //private final CRUDFireBase crudFireBase;
    public InformationLocationManager() {
        apiList = new LinkedList<>();
        apiList.add(APIsNames.WEATHER.getOrder(), new OpenWeather());
        apiList.add(APIsNames.EVENTS.getOrder(), new TicketMaster());
        apiList.add(APIsNames.NEWS.getOrder(), new NewsAPI());

        //crudFireBase = CRUDFireBase.getInstance();
    }

    public void setApiList(List<API> apiList) {
        this.apiList = apiList;
    }

    public List<API> getApiList() {
        return apiList;
    }

    public void changeApiState(int order, User user) throws NotExistingAPIException {
        if (order < 0 || order >= apiList.size())
            throw new NotExistingAPIException();
        API api=apiList.get(order);
            for (Location location: user.getUserLocations()){
                location.getApiManager().getApiList().get(order).setActive(!api.getIsActive());
            }
            api.setActive(!api.getIsActive());

    }
    public List<List<List<APIInformation>>> getAllActivatedInfo(User user) throws IndexOutOfBoundsException{
        List<List<List<APIInformation>>> list = new LinkedList<>();
        List<Location> locations = user.getUserLocations();
        if (locations==null)
            return list;
        for (Location location: locations){
            if (location.getIsActive()) {
                List<List<APIInformation>> listAux = new LinkedList<>();
                ApiFacade manager = location.getApiManager();
                manager.generateInfo(location.getName());

                for (int i = 0; i < apiList.size(); i++) {
                    if (!manager.getApiList().get(i).getIsActive()) {
                        listAux.add(new LinkedList<>());
                    } else {
                        listAux.add(manager.getInformation(i));
                    }
                }
                list.add(listAux);
            }

        }
        return list;
    }




    public List<API> getApis() { return apiList; }


}
