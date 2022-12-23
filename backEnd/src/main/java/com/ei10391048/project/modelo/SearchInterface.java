package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;

public interface SearchInterface {
    final String apiKey = "96a01f139118d49f85a54068a7321e3d";

    public Location search()  throws IncorrectLocationException;
}
