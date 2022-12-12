package com.ei10391048.project.modelo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


public abstract class APIInformation {



    String apiName;
    String locationName;
    LocalDateTime date;



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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


}
