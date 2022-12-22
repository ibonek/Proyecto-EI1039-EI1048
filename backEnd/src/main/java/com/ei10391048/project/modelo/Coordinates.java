package com.ei10391048.project.modelo;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.lat, lat) == 0 && Double.compare(that.lon, lon) == 0;
    }

}
