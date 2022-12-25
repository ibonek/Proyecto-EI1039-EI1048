package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NonExistingAPIException;
import com.ei10391048.project.modelo.api.*;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.LinkedList;
import java.util.List;

public class InformationLocationManager implements InformationLocationManagerFacade {
    private List<API> apiList;
    private static InformationLocationManagerFacade activationManager;


    private InformationLocationManager() {
        this.apiList = new LinkedList<>();

        apiList.add(APIsNames.WEATHER.getOrder(),new OpenWeather());
        apiList.add(APIsNames.EVENTS.getOrder(),new TicketMaster());
        apiList.add(APIsNames.NEWS.getOrder(),new NewsAPI());
    }

    public static InformationLocationManagerFacade getInstance() {
        if (activationManager == null) {
            activationManager = new InformationLocationManager();
        }
        return activationManager;

    }
    public void changeApiState(int order) throws NonExistingAPIException {
        if (order < 0 || order >= apiList.size())
            throw new NonExistingAPIException();
        API api=apiList.get(order);
        for (Location location: LocationManager.getInstance().getLocations()){
            location.getApiManager().getApiList().get(order).setActive(!api.getIsActive());
        }
        api.setActive(!api.getIsActive());
    }
    public List<List<List<APIInformation>>> getAllActivatedInfo() throws IndexOutOfBoundsException{
        List<List<List<APIInformation>>> list = new LinkedList<>();
        for (Location location: LocationManager.getInstance().getLocations()){
            List<List<APIInformation>> listAux = new LinkedList<>();
            ApiFacade manager = location.getApiManager();
            manager.generateInfo(location.getName());

            for (int i=0;i<apiList.size();i++){
                API api = apiList.get(i);
                if (!api.getIsActive()){
                    listAux.add(new LinkedList<>());
                }else {
                    listAux.add(manager.getInformation(i));
                }
            }
            list.add(listAux);

        }
        return list;
    }

    public void changeActiveState(String location) throws IncorrectLocationException {
        Location loc;
        try {
            loc = getLocation(location);
        } catch (IncorrectLocationException e) {
            throw new IncorrectLocationException();
        }
        loc.setActive(!loc.getIsActive());

    }
    public List<API> getApis() { return apiList; }

}
