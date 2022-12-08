package com.ei10391048.project.modelo;

import javax.persistence.*;

@Entity
public abstract class API {

    @Id
    @Column(name = "name", nullable = false)
    protected String name;

    public Information getInfo(){
        return null;
    }

    public String getAPIName() {
        return name;
    }

    public void setAPIName(String name) {
        this.name = name;
    }
}
