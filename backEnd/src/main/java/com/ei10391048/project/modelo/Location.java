package com.ei10391048.project.modelo;

import javax.persistence.*;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private Long locationId;
    private String name;

    @OneToOne
    private Coordinates coordinates;
    public Location(String name) {
        this.name = name;
    }

    public Location(Coordinates coordinates) {
        this.coordinates=coordinates;
    }

    public Location() {

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

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
