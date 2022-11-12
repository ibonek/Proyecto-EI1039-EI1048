package com.ei10391048.project.modelo;

import javax.persistence.*;

@Entity
public class Coordenadas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String latitud;
    private String longitud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordenadas(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Coordenadas(String latitudlongitud) {
        String[] latitudlongitudArray = latitudlongitud.split(",");
        this.latitud = latitudlongitudArray[0];
        this.longitud = latitudlongitudArray[1];
    }


    public Coordenadas() {

    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Coordenadas{" +
                "id=" + id +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }
}
