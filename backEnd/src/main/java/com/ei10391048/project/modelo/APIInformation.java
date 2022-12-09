package com.ei10391048.project.modelo;

import javax.persistence.*;
import java.util.Date;

@Entity
public class APIInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private Long id;

    String apiName;
    String locationName;
    Date date;
    boolean state;
    String informationBody;


    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getInformationBody() {
        return informationBody;
    }

    public void setInformationBody(String informationBody) {
        this.informationBody = informationBody;
    }


}
