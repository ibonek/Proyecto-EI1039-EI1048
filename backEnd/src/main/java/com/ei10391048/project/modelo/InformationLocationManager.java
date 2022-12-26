package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.NonExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.fireBase.CRUDFireBase;
import com.ei10391048.project.modelo.api.*;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.LinkedList;
import java.util.List;

public class InformationLocationManager implements InformationLocationManagerFacade {
    private final List<API> apiList;
    private static InformationLocationManagerFacade informationLocationManager;


    private InformationLocationManager() {
        this.apiList = new LinkedList<>();
        CRUDFireBase crudFireBase = new CRUDFireBase();
        apiList.add(APIsNames.WEATHER.getOrder(),new OpenWeather());
        apiList.add(APIsNames.EVENTS.getOrder(),new TicketMaster());
        apiList.add(APIsNames.NEWS.getOrder(),new NewsAPI());
        for (API api: apiList){
            try {
                crudFireBase.addAPI(api);
            } catch (NotSavedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static InformationLocationManagerFacade getInstance() {
        if ( informationLocationManager== null) {
            informationLocationManager = new InformationLocationManager();
        }
        return informationLocationManager;

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

    @Override
    public void changeAllAPIs(boolean active) {
        for (API api: apiList){
            api.setActive(active);
        }
    }


    public List<API> getApis() { return apiList; }


}
