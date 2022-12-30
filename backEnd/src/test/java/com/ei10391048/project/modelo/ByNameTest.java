package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ByNameTest {
    private ByName byNameMock;
    private LocationManager managerMock;

    @BeforeEach
    public void setUp(){
        byNameMock= Mockito.mock(ByName.class);
        managerMock = Mockito.mock(LocationManager.class);
    }

    @Test
    public void registerLocation_invalidName() throws IncorrectLocationException {
        when(byNameMock.search()).thenThrow(new IncorrectLocationException());

        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(byNameMock);
        when(managerMock.generateApiInterface(any(String.class))).thenReturn(geoCodService);
        try {
        when(managerMock.addUserLocation(any(String.class))).thenCallRealMethod();

            managerMock.addUserLocation("null");
            fail();
        } catch (IncorrectLocationException | NotSavedException ex){
            assertTrue(true);
        }

    }

    @Test
    public void registerLocation_validName() throws IncorrectLocationException, NotSavedException {
        Location place = new Location();

        place.setName("Barcelona");
        LocationManager manager = Mockito.spy(new LocationManager());
        int num = manager.getNumberOfLocations();

        manager.addUserLocation("Barcelona");

        Mockito.verify(manager).addUserLocation("Barcelona");

        assertEquals(num+1,manager.getNumberOfLocations());
    }



}

