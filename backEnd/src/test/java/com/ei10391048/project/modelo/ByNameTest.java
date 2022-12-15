package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ByNameTest {
    private ByName byNameMock;

    @BeforeEach
    public void setUp(){
        byNameMock= Mockito.mock(ByName.class);
    }
    @Test
    public void registerLocation_invalidName() throws IncorrectLocationException {
        when(byNameMock.search()).thenThrow(new IncorrectLocationException());
        LocationManager manager = LocationManager.getInstance();
        int num = manager.getNumberOfLocations();

        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(byNameMock);
        manager.setLocationApi(geoCodService);
        try {
            manager.addLocation();
            fail();
        } catch (IncorrectLocationException ex){
            assertTrue(true);
        }finally {
            assertEquals(num,manager.getNumberOfLocations());

        }

    }

    @Test
    public void registerLocation_validName() throws IncorrectLocationException {
        Location place = new Location();
        place.setName("Valencia");
        when(byNameMock.search()).thenReturn(place);

        LocationManager manager = LocationManager.getInstance();
        int num = manager.getNumberOfLocations();

        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(byNameMock);
        manager.setLocationApi(geoCodService);
        manager.addLocation();
        assertEquals(num+1,manager.getNumberOfLocations());
        assertEquals(manager.getLocations().get(num), place);
    }



}

