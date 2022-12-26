package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.List;

public interface InformationLocationManagerFacade {
    List<API> getApis();

    void changeApiState(int order) throws NotExistingAPIException;

    List<List<List<APIInformation>>> getAllActivatedInfo();

    void changeAllAPIs(boolean active);

}
