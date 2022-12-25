package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NonExistingAPIException;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.List;

public interface InformationLocationManagerFacade {
    List<API> getApis();
    void changeActiveState(String location) throws IncorrectLocationException;

    void changeApiState(int order) throws NonExistingAPIException;

    List<List<List<APIInformation>>> getAllActivatedInfo();
}
