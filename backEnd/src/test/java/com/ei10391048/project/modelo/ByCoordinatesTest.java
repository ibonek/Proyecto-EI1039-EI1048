package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.exception.NotSavedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ByCoordinatesTest {


    private ByCoordinates byCoordinatesMock;
    private LocationManager managerMock;

    @BeforeEach
    public void setUp(){
        byCoordinatesMock= Mockito.mock(ByCoordinates.class);
        managerMock = Mockito.mock(LocationManager.class);

    }

    @Test
    public void registerLocation_validCoords() throws IncorrectLocationException, NotSavedException, IncorrectUserException {
        Location place = new Location();
        place.setCoordinates(new Coordinates(-3.7025600,40.4165000));
        LocationManager manager = Mockito.spy(new LocationManager());
        int num = manager.getNumberOfLocations();
        manager.addUserLocation(new Coordinates(-3.7025600,40.4165000));
        Mockito.verify(manager).addUserLocation(new Coordinates(-3.7025600,40.4165000));
        assertEquals(num+1,manager.getNumberOfLocations());


    }

    @Test
    public void registerLocation_invalidCoords() throws IncorrectLocationException{
        when(byCoordinatesMock.search()).thenThrow(new IncorrectLocationException());

        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(byCoordinatesMock);
        when(managerMock.generateApiInterface(any(Coordinates.class))).thenReturn(geoCodService);
        try {
            when(managerMock.addUserLocation(any(Coordinates.class))).thenCallRealMethod();

            managerMock.addUserLocation(new Coordinates(50000,6000));
            fail();
        } catch (IncorrectLocationException | NotSavedException ex){
            assertTrue(true);
        }
    }


}
