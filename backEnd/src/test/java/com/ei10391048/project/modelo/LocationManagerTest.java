package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.AlreadyActiveLocation;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
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
    @BeforeEach
    void setUp(){
        LocationManager manager = LocationManager.getInstance();
        manager.clearLocations();
    }


    @ParameterizedTest
    @MethodSource("getName")
    void getLocationsNameValidCase(ArrayList<String> sol) throws IncorrectLocationException, NotSavedException {
        LocationManager manager = LocationManager.getInstance();
        for (String name : sol) {
            GeoCodService geoCodService = new GeoCodService();
            geoCodService.setSearch(new ByName(name));
            manager.setLocationApi(geoCodService);
            manager.addLocation();

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
        LocationManager manager = LocationManager.getInstance();
        manager.setLocations(new LinkedList<>());
        assertEquals(manager.getLocations(), new LinkedList<Location>());
    }

    @Test
    void activateLocationValidCase() throws IncorrectLocationException, NotSavedException{
        LocationManager manager = LocationManager.getInstance();
        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(new ByName("Castellón"));
        manager.setLocationApi(geoCodService);
        manager.addLocation();
        geoCodService.setSearch(new ByName("Madrid"));
        manager.setLocationApi(geoCodService);
        manager.addLocation();
        manager.getLocations().get(1).setActive(false);
        manager.changeActiveState("Madrid");
        assertTrue(manager.getLocations().get(1).getIsActive());
    }

    @Test
    void activateLocationInvalidCase() throws IncorrectLocationException, NotSavedException {
        try {
            LocationManager manager = LocationManager.getInstance();
            GeoCodService geoCodService = new GeoCodService();
            geoCodService.setSearch(new ByName("Castellón"));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
            geoCodService.setSearch(new ByName("Madrid"));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
            manager.getLocations().get(1).setActive(false);

            manager.changeActiveState("Castellonn");
            fail();
        } catch (IncorrectLocationException e){
            assertTrue(true);
        }
    }

    @ParameterizedTest
    @MethodSource("getActiveLocation")
    void getActiveLocationValidCase(ArrayList<String> sol) throws IncorrectLocationException,  NotSavedException {
        LocationManager manager = LocationManager.getInstance();
        GeoCodService geoCodService = new GeoCodService();
        for (String name : sol) {
            geoCodService.setSearch(new ByName(name));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
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
        LocationManager manager = LocationManager.getInstance();
        GeoCodService geoCodService = new GeoCodService();
        for (String name : input) {
            geoCodService.setSearch(new ByName(name));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
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
        LocationManager manager = LocationManager.getInstance();
        GeoCodService geoCodService = new GeoCodService();
        for (String name : input) {
            geoCodService.setSearch(new ByName(name));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
            manager.changeActiveState(name);
            assertNotEquals(manager.getLocation(name).getIsActive(), true);
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
}