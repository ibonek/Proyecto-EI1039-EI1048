package com.ei10391048.project.modelo.api;

import com.ei10391048.project.modelo.APIInformation;

import javax.persistence.*;


public abstract class API {


    protected String name;


    String apiKey=null;


    public APIInformation generateInfo(String locationName)  {
        return setInfoData(locationName);

    }
    private APIInformation setInfoData(String locationName) {

        if (apiCall(locationName)){
            insertAPIName();
            insertLocationName();
            insertDate();
            insertBodyData();
            return getInfo();
        }
        return null;

        }




    public String getAPIName() {
        return name;
    }

    abstract void insertAPIName();

    abstract void insertLocationName();


    abstract void insertDate();
    abstract boolean apiCall(String locationName);

    //Tiene que ser lista cuando añadas otra API

    abstract APIInformation getInfo();

    abstract void insertBodyData();

}
