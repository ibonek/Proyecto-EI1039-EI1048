package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.fireBase.CRUDFireBase;
import com.ei10391048.project.modelo.api.*;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.LinkedList;
import java.util.List;

public class InformationLocationManager implements InformationLocationManagerFacade {
    private List<API> apiList;
    private static InformationLocationManagerFacade informationLocationManager;
    private final CRUDFireBase crudFireBase;


    private InformationLocationManager() {
        crudFireBase = new CRUDFireBase();
        try {
            apiList=crudFireBase.getAPIs();
        } catch (NotExistingAPIException e) {
            apiList = new LinkedList<>();
            apiList.add(APIsNames.WEATHER.getOrder(),new OpenWeather());
            apiList.add(APIsNames.EVENTS.getOrder(),new TicketMaster());
            apiList.add(APIsNames.NEWS.getOrder(),new NewsAPI());
            for (API api: apiList){
                try {
                    crudFireBase.addAPI(api);
                } catch (NotSavedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public static InformationLocationManagerFacade getInstance() {
        if ( informationLocationManager== null) {
            informationLocationManager = new InformationLocationManager();
        }
        return informationLocationManager;

    }
    public void changeApiState(int order) throws NotExistingAPIException {
        if (order < 0 || order >= apiList.size())
            throw new NotExistingAPIException();
        API api=apiList.get(order);
        try {
            for (Location location: LocationManager.getInstance().getLocations()){
                crudFireBase.changeAPILocationStatus(location.getApiManager().getApiList().get(order),location);
                location.getApiManager().getApiList().get(order).setActive(!api.getIsActive());
            }
            crudFireBase.changeAPIStatus(api);
            api.setActive(!api.getIsActive());
        } catch (NotSavedException e) {
            throw new RuntimeException(e);
        }
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
