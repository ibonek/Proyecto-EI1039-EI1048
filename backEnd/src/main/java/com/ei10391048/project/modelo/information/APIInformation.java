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
        String[] aux = date.split("T");
        this.date = aux[0]+": "+aux[1];
        if (this.date.contains("Z")){
            this.date = this.date.substring(0,this.date.length()-1);
        }
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }
}
