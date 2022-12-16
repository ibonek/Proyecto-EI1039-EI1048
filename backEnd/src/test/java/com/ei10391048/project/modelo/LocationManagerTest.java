package com.ei10391048.project.modelo;

import com.ei10391048.project.controlador.InputValidator;
import com.ei10391048.project.exception.AlreadyActiveLocation;
import com.ei10391048.project.exception.IncorrectLocationException;
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
    void getLocationsNameValidCase(ArrayList<String> sol) throws IncorrectLocationException {
        LocationManager manager = LocationManager.getInstance();
        GeoCodService geoCodService = new GeoCodService();
        for (String name : sol) {
            geoCodService.setSearch(new ByName(name));
            manager.setLocationApi(geoCodService);
            manager.addLocation();

        }

        assertEquals(manager.getLocationsName(), sol);
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
        manager.setLocations(new LinkedList<Location>());
        assertEquals(manager.getLocations(), new LinkedList<Location>());
    }

    @ParameterizedTest
    @MethodSource("getActiveLocation")
    void getActiveLocationValidCase(ArrayList<String> sol) throws IncorrectLocationException, AlreadyActiveLocation {
        LocationManager manager = LocationManager.getInstance();
        GeoCodService geoCodService = new GeoCodService();
        for (String name : sol) {
            geoCodService.setSearch(new ByName(name));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
            manager.activeLocation(name);
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
    void getActiveLocationInvalidCase(ArrayList<String> input,ArrayList<String> sol) throws IncorrectLocationException, AlreadyActiveLocation {
        LocationManager manager = LocationManager.getInstance();
        GeoCodService geoCodService = new GeoCodService();
        for (String name : input) {
            geoCodService.setSearch(new ByName(name));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
            manager.activeLocation(name);
        }
        for (String name : sol){
            manager.activeLocation(name);
        }

        assertEquals(manager.getActiveLocationsName(), sol);

    }

    static Stream<Arguments> getActiveLocationInvalid() {

        ArrayList<String> input = new ArrayList<>();
        addAll(input, "Valencia","Madrid","Beijing");
        ArrayList<String> input2 = new ArrayList<>();
        addAll(input2, "Montevideo","Castellon","London");
        ArrayList<String> sol = new ArrayList<>();
        addAll(sol, "Madrid","Beijing");
        ArrayList<String> sol2 = new ArrayList<>();
        addAll(sol2, "Castellon","London");
        return Stream.of(
                Arguments.of(input, sol),
                Arguments.of(input2, sol2)
        );
    }
}