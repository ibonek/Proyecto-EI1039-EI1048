package com.ei10391048.project.modelo;

import javax.persistence.*;

@Entity
public abstract class API {

    @Id
    @Column(name = "name", nullable = false)
    private String name;


    String apiKey=null;

    @OneToOne
    private APIInformation information;
    public APIInformation getInfo(String locationName){
        setInfo(locationName);
        return this.information;
    }
    private void setInfo(String locationName){
        APIInformation information = new APIInformation();
        apiCall(locationName);
        information.setApiName(this.insertAPIName());
        information.setLocationName(this.insertLocationName());


        this.information=information;
    }

    public String getAPIName() {
        return name;
    }

    abstract String insertAPIName();

    abstract String insertLocationName();


    abstract void apiCall(String locationName);
}
