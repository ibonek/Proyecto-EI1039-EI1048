package com.ei10391048.project.modelo;

import com.ei10391048.project.modelo.information.APIInformation;
import com.ei10391048.project.modelo.information.EventInformation;
import com.ei10391048.project.modelo.information.NewsInformation;
import com.ei10391048.project.modelo.information.WeatherInformation;

import java.util.List;

public interface ApiFacade {
    List<APIInformation> getWeatherInformation();
    List<APIInformation> getEventsInformation();
    List<APIInformation> getNewsInformation();

    void generateInfo(String locationName);

}
