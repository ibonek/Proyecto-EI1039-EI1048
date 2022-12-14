package com.ei10391048.project.modelo.information;

public class EventInformation extends APIInformation{

    private String eventName;

    private String address;

    private String place;



    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEventName() {
        return eventName;
    }

    public String getAddress() {
        return address;
    }

    public String getPlace() {
        return place;
    }
    public void setDate(String date) {
        String[] aux = date.split("T");
        this.date = aux[0]+": "+aux[1];
        if (this.date.contains("Z")){
            this.date = this.date.substring(0,this.date.length()-1);
        }
    }

    @Override
    public String toString() {
        return "EventInformation{" +
                "eventName='" + eventName + '\'' +
                ", address='" + address + '\'' +
                ", place='" + place + '\'' +
                ", apiName='" + apiName + '\'' +
                ", locationName='" + locationName + '\'' +
                ", date=" + date +
                ", imageURL="+ imageURL+
                '}';
    }
}
