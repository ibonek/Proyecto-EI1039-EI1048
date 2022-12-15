package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.NonActiveServiceException;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.APIsNames;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.LinkedList;
import java.util.List;


public class APIManager {


    private List<API> apiList;

    public APIManager() {
        apiList = new LinkedList<>();
    }


    public void addAPI(API type){
        apiList.add(type);
    }

    public API getAPI(APIsNames name)  throws NonActiveServiceException {

        for (API api: apiList){
            if (api.getAPIName().equals(name.getApiName())){
                return api;
            }
        }

        throw new NonActiveServiceException();
    }


    public List<List<APIInformation>> getInfo(String locationName) {
        List<List<APIInformation>> list = new LinkedList<>();
        for (API api: apiList){
            list.add(api.generateInfo(locationName));
        }
        return list;

    }
}
