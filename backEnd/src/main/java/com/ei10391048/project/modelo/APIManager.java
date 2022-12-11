package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.NonActiveServiceException;
import com.ei10391048.project.modelo.api.API;

import javax.persistence.*;
import java.util.LinkedList;

@Entity
public class APIManager {
    @Id
    @Column(name="id", nullable = false)
    private Long id;

    @OneToMany
    private LinkedList<API> apiList;

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


    public APIInformation getInfo(String locationName) {
        API api = apiList.get(0);
        return api.generateInfo(locationName);

    }
}
