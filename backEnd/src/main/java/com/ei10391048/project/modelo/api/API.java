package com.ei10391048.project.modelo.api;

import com.ei10391048.project.modelo.information.APIInformation;

import java.util.LinkedList;
import java.util.List;


public abstract class API {

    protected String name;

    protected List<APIInformation> information= new LinkedList<>();
    protected String apiKey=null;
    protected boolean isActive=true;


    public List<APIInformation> generateInfo(String locationName)  {
        if (!information.isEmpty()){
            return information;
        } else {
            return setInfoData(locationName);
        }
    }
    private List<APIInformation> setInfoData(String locationName) {

        if (apiCall(locationName)){
            insertBodyData();
            insertAPIName();
            insertLocationName();
            insertDate();
            insertImageURL();
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


    List<APIInformation> getInfo(){
            return information;

    }

    abstract void insertBodyData();

    public String getName() {
        return name;
    }

    public List<APIInformation> getInformation() {
        return information;
    }

    public String getApiKey() {
        return apiKey;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInformation(List<APIInformation> information) {
        this.information = information;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
