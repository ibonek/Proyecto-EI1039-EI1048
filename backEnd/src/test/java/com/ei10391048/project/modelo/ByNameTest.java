package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.fireBase.CRUDFireBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ByNameTest {
    private ByName byNameMock;
    private LocationApiInterface geoCodMock;

    private LocationManager managerMock;
    private CRUDFireBase crudFireBaseMock;

    @BeforeEach
    public void setUp(){
        byNameMock= Mockito.mock(ByName.class);
        geoCodMock = Mockito.mock(GeoCodService.class);
        managerMock = Mockito.mock(LocationManager.class);
        crudFireBaseMock = Mockito.mock(CRUDFireBase.class);
    }
    @Test
    public void registerLocation_invalidName() throws IncorrectLocationException {
        when(byNameMock.search()).thenThrow(new IncorrectLocationException());

        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(byNameMock);
        when(managerMock.generateApiInterface(any(String.class))).thenReturn(geoCodService);
        try {
        when(managerMock.addLocation(any(String.class))).thenCallRealMethod();

            managerMock.addLocation("null");
            fail();
        } catch (IncorrectLocationException | NotSavedException ex){
            assertTrue(true);
        }

    }

    @Test
    public void registerLocation_validName() throws IncorrectLocationException, NotSavedException {
        Location place = new Location();
        geoCodMock.setSearch(byNameMock);
        place.setName("Barcelona");
        LocationManager manager = (LocationManager) Mockito.spy(LocationManager.getInstance());
        when(byNameMock.search()).thenReturn(place);
        when(manager.generateApiInterface(any(String.class))).thenReturn(geoCodMock);
        Location location =manager.addLocation("Barcelona");

        Mockito.verify(manager).addLocation("Barcelona");




        assertEquals(location, place);
    }



}

