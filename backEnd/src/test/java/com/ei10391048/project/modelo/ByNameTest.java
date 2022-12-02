package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
/*
class ByNameTest {
    private OpenWeatherMapClient mock;
    private String apiKey = "4d8fb5b93d4af21d66a2948710284366";

    @ParameterizedTest
    @MethodSource("cities")
    public void testAPIFindingLocationByName(String city) throws IncorrectLocationException {
        ByName mock = Mockito.mock(ByName.class);
        when(mock.search().getName()).thenReturn(city);
        OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient(apiKey);
        String result = openWeatherMapClient.currentWeather().single().byCityName(city).language(Language.SPANISH).unitSystem(UnitSystem.METRIC).retrieve().asJava().getLocation().getName();
        assertEquals(city, result);
        Mockito.verify(mock).search().getCoordinates();
    }

    static Stream<Arguments> cities() {
        return Stream.of(
                Arguments.of("Madrid"),
                Arguments.of("Valencia"),
                Arguments.of("Torreblanca"),
                Arguments.of("Melilla")
        );
    }
}

 */