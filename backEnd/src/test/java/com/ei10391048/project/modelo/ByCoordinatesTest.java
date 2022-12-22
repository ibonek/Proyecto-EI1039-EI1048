package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ByCoordinatesTest {
    private ByCoordinates byCoordinatesMock;

    @BeforeEach
    public void setUp(){
        byCoordinatesMock= Mockito.mock(ByCoordinates.class);
    }

    @Test
    public void registerLocation_validCoords() throws IncorrectLocationException, NotSavedException {
        Location place = new Location();
        place.setCoordinates(new Coordinates(-3.7025600,40.4165000));
        when(byCoordinatesMock.search()).thenReturn(place);

        LocationManager manager = LocationManager.getInstance();
        int num = manager.getNumberOfLocations();

        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(byCoordinatesMock);
        manager.setLocationApi(geoCodService);
        manager.addLocation();
        assertEquals(num+1,manager.getNumberOfLocations());
        assertEquals(manager.getLocations().get(num), place);
    }

    @Test
    public void registerLocation_invalidCoords() throws IncorrectLocationException{
        when(byCoordinatesMock.search()).thenThrow(new IncorrectLocationException());
        LocationManager manager = LocationManager.getInstance();
        int num = manager.getNumberOfLocations();

        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(byCoordinatesMock);
        manager.setLocationApi(geoCodService);
        try {
            manager.addLocation();
            fail();
        } catch (IncorrectLocationException | NotSavedException ex){
            assertTrue(true);
        }finally {
            assertEquals(num,manager.getNumberOfLocations());

        }
    }
}
