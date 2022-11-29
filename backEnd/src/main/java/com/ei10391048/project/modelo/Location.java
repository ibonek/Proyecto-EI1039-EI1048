package com.ei10391048.project.modelo;

public class Location {
    private String name;

    private Coordinates coordinates;
    public Location(String name) {
        this.name = name;
    }

    public Location(Coordinates coordinates) {
        this.coordinates=coordinates;
    }

    public String getName() {
        return name;
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
}
