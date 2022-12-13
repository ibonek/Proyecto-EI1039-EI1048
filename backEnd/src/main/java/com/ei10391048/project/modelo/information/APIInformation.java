package com.ei10391048.project.modelo.information;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


public abstract class APIInformation {



    String apiName;
    String locationName;
    String date;

    String imageURL;



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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }
}
