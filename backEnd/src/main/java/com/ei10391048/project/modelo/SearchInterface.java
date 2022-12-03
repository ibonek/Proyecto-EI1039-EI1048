package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;

public interface SearchInterface {
    final String apiKey = "4d8fb5b93d4af21d66a2948710284366";

    public Location search()  throws IncorrectLocationException;
}
