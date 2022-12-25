package com.ei10391048.project.modelo;

import com.ei10391048.project.modelo.information.APIInformation;

import java.util.List;

public interface ApiFacade {
    List<APIInformation> getInformation(int order);

    void generateInfo(String locationName);

}
