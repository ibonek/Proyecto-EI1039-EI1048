package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NonExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.api.API;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.addAll;
import static org.junit.jupiter.api.Assertions.*;

class LocationManagerTest {
    LocationManagerFacade manager =  LocationManager.getInstance();
    @BeforeEach
    void setUp(){
        manager.clearLocations();
    }

    /**
     * Test que comprueba la historia de usuario 6: Como usuario quiero dar de alta una ubicación a partir de un
     * topónimo, con el fin de tenerla disponible en el sistema.
     *
     * @throws IncorrectLocationException
     */
    @Test
    public void addLocationByNameValid() throws IncorrectLocationException, NotSavedException {
        String name = "Valencia";

        LocationManagerFacade locations = LocationManager.getInstance();
        int num = locations.getNumberOfLocations();
        locations.addLocation(name);

        assertEquals( locations.getNumberOfLocations(), num + 1);
    }


    @Test
    public void addLocationByNameInvalid() {
        String name = "";
        try {
            manager.addLocation(name);
            fail();
        } catch (IncorrectLocationException ex){
            int num = manager.getNumberOfLocations();
            assertEquals(0,num);
        } catch (NotSavedException e) {
            fail();
        }
    }


    /**
     * Test que comprueba la historia de usuario 7: Como usuario quiero dar de alta una ubicación a partir de unas
     * coordenadas geográficas, con el fin de tenerla disponible en el sistema.
     *
     * @throws IncorrectLocationException
     */
    @Test
    public void addLocationByCoordinatesValid() throws IncorrectLocationException, NotSavedException {
        Coordinates coordinates = new Coordinates(-0.3773900,39.4697500);
        int num = manager.getNumberOfLocations();
        manager.addLocation(coordinates);
        assertEquals(manager.getNumberOfLocations(), num + 1);
    }


    @ParameterizedTest
    @MethodSource("coords")
    public void addLocationByCoordinatesInvalid(double lat, double lon){
        LocationManagerFacade locationManager = LocationManager.getInstance();
        try {
            Coordinates coordinates = new Coordinates(lat,lon);
            locationManager.addLocation(coordinates);
        } catch (IncorrectLocationException ex){
            int num = locationManager.getNumberOfLocations();
            assertEquals(0,num);
        } catch (NotSavedException e) {
            fail();
        }
    }

    static Stream<Arguments> coords(){
        return Stream.of(
                Arguments.of(-190,100),
                Arguments.of(190,100),
                Arguments.of(100,190),
                Arguments.of(100,-190)
        );
    }


    @ParameterizedTest
    @MethodSource("getName")
    void getLocationsNameValidCase(ArrayList<String> sol) throws IncorrectLocationException, NotSavedException {
        for (String name : sol) {
            manager.addLocation(name);

        }

        assertEquals(manager.getActiveLocationsName(), sol);
    }

    static Stream<Arguments> getName() {

        ArrayList<String> sol = new ArrayList<>();
        addAll(sol, "Valencia","Madrid","Beijing");
        ArrayList<String> sol2 = new ArrayList<>();
        addAll(sol2, "Montevideo","Castellon","London");
        return Stream.of(
                Arguments.of( sol),
                Arguments.of( sol2)
        );
    }

    @Test
    void getLocationsNameInvalidCase() {
        manager.setLocations(new LinkedList<>());
        assertEquals(manager.getLocations(), new LinkedList<Location>());
    }


    @ParameterizedTest
    @MethodSource("getActiveLocation")
    void getActiveLocationValidCase(ArrayList<String> sol) throws IncorrectLocationException,  NotSavedException {
        for (String name : sol) {
            manager.addLocation(name);
        }
        assertEquals(manager.getActiveLocationsName(), sol);
    }

    static Stream<Arguments> getActiveLocation() {

        ArrayList<String> sol = new ArrayList<>();
        addAll(sol, "Valencia","Madrid","Beijing");
        ArrayList<String> sol2 = new ArrayList<>();
        addAll(sol2, "Montevideo","Castellon","London");
        return Stream.of(
                Arguments.of( sol),
                Arguments.of( sol2)
        );
    }

    @ParameterizedTest
    @MethodSource("getActiveLocationInvalid")
    void getActiveLocationInvalidCase(ArrayList<String> input) throws IncorrectLocationException, NotSavedException {
        for (String name : input) {
            manager.addLocation(name);
        }


        assertNotEquals(manager.getActiveLocationsName().size(), 0);

    }

    static Stream<Arguments> getActiveLocationInvalid() {

        ArrayList<String> input = new ArrayList<>();
        addAll(input, "Valencia","Madrid","Beijing");
        ArrayList<String> input2 = new ArrayList<>();
        addAll(input2, "Montevideo","Castellon","London");
        return Stream.of(
                Arguments.of(input),
                Arguments.of(input2)
        );
    }

    @ParameterizedTest
    @MethodSource("getInactiveLocationValidCase")
    void getInactiveLocationValidCase(ArrayList<String> input) throws IncorrectLocationException, NotSavedException {
        for (String name : input) {
            manager.addLocation(name);
            manager.getActivationManager().changeActiveState(name);
            assertFalse(manager.getLocation(name).getIsActive());
        }

    }

    static Stream<Arguments> getInactiveLocationValidCase() {

        ArrayList<String> input = new ArrayList<>();
        addAll(input, "Valencia","Madrid","Beijing");
        ArrayList<String> input2 = new ArrayList<>();
        addAll(input2, "Montevideo","Castellon","London");
        return Stream.of(
                Arguments.of(input),
                Arguments.of(input2)
        );
    }

    @Test
    void deleteLocationValidCase(){
        try {
            manager.addLocation("Valencia");
            manager.deleteLocation("Valencia");
            assertTrue(true);
        } catch (IncorrectLocationException e) {
            fail();
        } catch (NotSavedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteLocationInvalidCase(){
        try {
            manager.deleteLocation("Valencia");
            fail();
        } catch (IncorrectLocationException e) {
            assertTrue(true);
        }
    }

}