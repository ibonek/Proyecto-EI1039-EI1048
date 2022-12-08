package com.ei10391048.project.modelo;

import javax.persistence.*;
import java.util.LinkedList;

@Entity
public class APIManager {
    @Id
    @Column(name="id", nullable = false)
    private Long id;

    private static APIManager apiManager;

    @OneToMany
    private LinkedList<API> apiList;

    public APIManager() {
        apiList = new LinkedList<>();
    }


    public void addAPI(API type){
        apiList.add(type);
    }

    public API getAPI(APIsNames name){
        for (API api: apiList){
            if (api.getAPIName().equals(name.getApiName())){
                return api;
            }
        }

        return null;
    }
}
