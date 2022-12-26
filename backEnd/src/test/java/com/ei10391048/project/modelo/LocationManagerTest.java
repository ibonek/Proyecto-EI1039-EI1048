package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.stream.Stream;

import static java.util.Collections.addAll;
import static org.junit.jupiter.api.Assertions.*;

class LocationManagerTest {
    static LocationManager manager;
    static User user;
    @BeforeAll
    static void setUp(){
        user = new User();
        manager = user.getLocationManager();
    }
    @BeforeEach
    void clear(){
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

        int num = user.getNumberOfLocations();
        user.addLocation(name);

        assertEquals( user.getNumberOfLocations(), num + 1);
    }


    @Test
    public void addLocationByNameInvalid() {
        String name = "";
        try {
            user.addLocation(name);
            fail();
        } catch (IncorrectLocationException ex){
            int num = user.getNumberOfLocations();
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
        int num = user.getNumberOfLocations();
        user.addLocation(coordinates);
        assertEquals(user.getNumberOfLocations(), num + 1);
    }


    @ParameterizedTest
    @MethodSource("coords")
    public void addLocationByCoordinatesInvalid(double lat, double lon){
        try {
            Coordinates coordinates = new Coordinates(lat,lon);
            user.addLocation(coordinates);
        } catch (IncorrectLocationException ex){
            int num = user.getNumberOfLocations();
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
            user.addLocation(name);
        }

        assertEquals(user.getActiveLocationsName(), sol);
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
        assertEquals(user.getMyLocations(), new LinkedList<Location>());
    }


    @ParameterizedTest
    @MethodSource("getActiveLocation")
    void getActiveLocationValidCase(ArrayList<String> sol) throws IncorrectLocationException,  NotSavedException {
        for (String name : sol) {
            user.addLocation(name);
        }
        assertEquals(user.getActiveLocationsName(), sol);
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
            user.addLocation(name);
        }


        assertNotEquals(user.getActiveLocationsName().size(), 0);

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


    @Test
    void deleteLocationValidCase(){
        try {
            user.addLocation("Valencia");
            user.deleteLocation("Valencia");
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
            user.deleteLocation("Valencia");
            fail();
        } catch (IncorrectLocationException e) {
            assertTrue(true);
        }
    }

}