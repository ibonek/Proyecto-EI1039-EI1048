package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ByCoordinatesTest {
    private ByCoordinates byCoordinatesMock;
    private LocationApiInterface geoCodMock;

    @BeforeEach
    public void setUp(){
        byCoordinatesMock= Mockito.mock(ByCoordinates.class);
        geoCodMock = Mockito.mock(GeoCodService.class);

    }

    @Test
    public void registerLocation_validCoords() throws IncorrectLocationException, NotSavedException {
        Location place = new Location();
        place.setCoordinates(new Coordinates(-3.7025600,40.4165000));
        when(byCoordinatesMock.search()).thenReturn(place);
        when(geoCodMock.setSearch(any(SearchInterface.class))).then(invocation -> {
            invocation = (InvocationOnMock) byCoordinatesMock;
            return invocation;
        });

        LocationManagerFacade manager = LocationManager.getInstance();
        int num = manager.getNumberOfLocations();

        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(byCoordinatesMock);
        manager.setLocationApi(geoCodService);
        manager.addLocation(new Coordinates(-3.7025600,40.4165000));
        assertEquals(num+1,manager.getNumberOfLocations());
        assertEquals(manager.getLocations().get(num), place);
        manager.setLocationApi(new GeoCodService());

    }

    @Test
    public void registerLocation_invalidCoords() throws IncorrectLocationException{
        when(byCoordinatesMock.search()).thenThrow(new IncorrectLocationException());
        when(geoCodMock.setSearch(any(SearchInterface.class))).then(invocation -> {
            invocation = (InvocationOnMock) byCoordinatesMock;
            return invocation;
        });
        LocationManagerFacade manager = LocationManager.getInstance();
        int num = manager.getNumberOfLocations();


        GeoCodService geoCodService = new GeoCodService();
        manager.setLocationApi(geoCodService);
        geoCodService.setSearch(byCoordinatesMock);
        try {
            manager.addLocation(new Coordinates(-3.7025600,40.4165000));
            fail();
        } catch (IncorrectLocationException | NotSavedException ex){
            assertTrue(true);
        }finally {
            assertEquals(num,manager.getNumberOfLocations());
            manager.setLocationApi(new GeoCodService());

        }
    }
}
