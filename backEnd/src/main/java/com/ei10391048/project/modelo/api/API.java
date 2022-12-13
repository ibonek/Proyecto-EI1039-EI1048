package com.ei10391048.project.modelo.api;

import com.ei10391048.project.modelo.information.APIInformation;
import com.ei10391048.project.modelo.information.EventInformation;
import com.ei10391048.project.modelo.information.WeatherInformation;

import java.util.LinkedList;
import java.util.List;


public abstract class API {

    protected String name;


    protected List<APIInformation> information= new LinkedList<>();;
    protected String apiKey=null;


    public List<APIInformation> generateInfo(String locationName)  {
        return setInfoData(locationName);

    }
    private List<APIInformation> setInfoData(String locationName) {

        if (apiCall(locationName)){
            insertAPIName();
            insertLocationName();
            insertDate();
            insertImageURL();
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


    abstract void insertImageURL();
    abstract void insertDate();
    abstract boolean apiCall(String locationName);

    //Tiene que ser lista cuando añadas otra API

    List<APIInformation> getInfo(){

            return information;

    }

    abstract void insertBodyData();

}
