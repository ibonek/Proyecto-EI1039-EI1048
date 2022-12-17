package com.ei10391048.project.modelo;

import javax.persistence.*;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private Long locationId;
    private String name;
    private boolean isActive;
    @OneToOne
    private Coordinates coordinates;
    public Location(String name) {
        this.name = name;
        isActive=true;
    }

    public Location(Coordinates coordinates) {
        this.coordinates=coordinates;
        isActive=true;
    }

    public Location() {

    }
    public Location(double lat, double lon) {
        this.coordinates = new Coordinates(lat,lon);

    }

    public String getName() {
        return name;
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
