package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.fireBase.CRUDFireBase;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ByNameTest {
    private ByName byNameMock;
    private CRUDFireBase firebaseMock;

    @BeforeEach
    public void setUp(){
        byNameMock= Mockito.mock(ByName.class);
        firebaseMock = Mockito.mock(CRUDFireBase.class);
    }
    @Test
    public void registerLocation_invalidName() throws IncorrectLocationException {
        when(byNameMock.search()).thenThrow(new IncorrectLocationException());
        LocationManagerFacade manager = LocationManager.getInstance();
        manager.setCrudFireBase(firebaseMock);
        int num = manager.getNumberOfLocations();

        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(byNameMock);
        manager.setLocationApi(geoCodService);
        try {
            manager.addLocation("null");
            fail();
        } catch (IncorrectLocationException | NotSavedException ex){
            assertTrue(true);
        }finally {
            assertEquals(num,manager.getNumberOfLocations());

        }

    }

    @Test
    public void registerLocation_validName() throws IncorrectLocationException, NotSavedException {
        Location place = new Location();
        place.setName("Barcelona");
        when(byNameMock.search()).thenReturn(place);



        LocationManagerFacade manager = LocationManager.getInstance();
        manager.setCrudFireBase(firebaseMock);
        int num = manager.getNumberOfLocations();

        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(byNameMock);
        manager.setLocationApi(geoCodService);
        manager.addLocation("Barcelona");
        assertEquals(num+1, manager.getNumberOfLocations());
        assertEquals(manager.getLocations().get(num), place);
    }



}

