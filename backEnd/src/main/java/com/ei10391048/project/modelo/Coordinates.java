package com.ei10391048.project.modelo;



public class Coordinates {
    private double lat;
    private double lon;


    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Coordinates() {

    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    @Override
    public String toString() {
        return "Coordinates{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
